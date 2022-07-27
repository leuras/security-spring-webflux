package com.mercadolivre.challenge.business.builder

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.entity.JWTTokenPart
import com.mercadolivre.challenge.entity.JWTTokenResponse
import com.mercadolivre.challenge.entity.ReasonCode
import org.springframework.security.core.userdetails.User
import java.time.Instant
import java.util.concurrent.TimeUnit

class JWTTokenBuilder(principal: User) {

    private val accessToken = TokenDefinition(principal)
    private val refreshToken = TokenDefinition(principal)
    private var algorithm: Algorithm? = null

    companion object {
        private const val ROLES = "roles"
        private const val ISSUER = "authorization-server.mercadolivre.com"
    }

    fun accessToken(): TokenDefinition = this.accessToken

    fun refreshToken(): TokenDefinition = this.refreshToken

    fun withAlgorithm(algorithm: Algorithm): JWTTokenBuilder {
        this.algorithm = algorithm

        return this
    }

    fun build(): JWTTokenResponse {
        this.algorithm?.let {
            val accessTokenValue = this.accessToken.configure(it)
            val refreshTokenValue = this.refreshToken.configure(it)

            return JWTTokenResponse(
                accessToken = accessTokenValue.token,
                expiresAt = accessTokenValue.expiresAt,
                refreshToken = refreshTokenValue.token,
                refreshTokenExpiresAt = refreshTokenValue.expiresAt
            )
        } ?: throw ReasonCodeException(ReasonCode.ALGORITHM_NOT_DEFINED)
    }

    inner class TokenDefinition(private val principal: User) {

        private val token = JWT.create()
        private var expiresAt: Instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(1))

        init {
            this.token.withSubject(this.principal.username)
        }

        fun withDefaultIssuer(): TokenDefinition {
            this.token.withIssuer(ISSUER)

            return this
        }

        fun expiresAfter(time: Long): TokenDefinition {
            this.expiresAt = Instant.now().plusMillis(time)
            this.token.withExpiresAt(this.expiresAt)

            return this
        }

        fun withRoles(): TokenDefinition {
            this.token.withClaim(ROLES, this.principal.authorities.map { it.authority })

            return this
        }

        fun and() = this@JWTTokenBuilder

        fun configure(algorithm: Algorithm): JWTTokenPart {
            return try {
                JWTTokenPart(
                    token = this.token.sign(algorithm),
                    expiresAt = this.expiresAt
                )
            } catch (t: Throwable) {
                throw ReasonCodeException(ReasonCode.MALFORMED_TOKEN)
            }
        }
    }
}

