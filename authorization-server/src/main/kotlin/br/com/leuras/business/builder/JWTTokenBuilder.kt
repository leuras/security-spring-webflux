package br.com.leuras.business.builder

import br.com.leuras.business.exception.ReasonCodeException
import br.com.leuras.entity.JWTTokenPart
import br.com.leuras.entity.JWTTokenResponse
import br.com.leuras.entity.ReasonCode
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.userdetails.User
import java.time.Instant
import java.util.concurrent.TimeUnit

class JWTTokenBuilder(principal: User) {
    private val accessToken = TokenDefinition(principal)
    private val refreshToken = TokenDefinition(principal)
    private var issuer: String? = null

    companion object {
        const val DEFAULT_ISSUER = "authorization-server.leuras.com.br"
    }

    fun accessToken(): TokenDefinition = this.accessToken

    fun refreshToken(): TokenDefinition = this.refreshToken

    fun withDefaultIssuer(): JWTTokenBuilder {
        this.issuer = DEFAULT_ISSUER

        return this
    }

    fun build(): JWTTokenResponse {
        return try {
            val accessTokenValue = this.accessToken.configure()
            val refreshTokenValue = this.refreshToken.configure()

            JWTTokenResponse(
                accessToken = accessTokenValue.token,
                expiresAt = accessTokenValue.expiresAt,
                refreshToken = refreshTokenValue.token,
                refreshTokenExpiresAt = refreshTokenValue.expiresAt
            )
        } catch (t: Throwable) {
            when (t) {
                is ReasonCodeException -> throw t
                else -> throw ReasonCodeException(ReasonCode.MALFORMED_TOKEN, t)
            }
        }
    }

    inner class TokenDefinition(private val principal: User) {
        private val token = JWT.create()
        private var expiresAt: Instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(1))
        private var algorithm: Algorithm? = null

        init {
            this.token.withSubject(this.principal.username)
        }

        fun withAlgorithm(algorithm: Algorithm): TokenDefinition {
            this.algorithm = algorithm

            return this
        }

        fun expiresAfter(time: Long): TokenDefinition {
            this.expiresAt = Instant.now().plusMillis(time)
            this.token.withExpiresAt(this.expiresAt)

            return this
        }

        fun and() = this@JWTTokenBuilder

        fun configure(): JWTTokenPart {
            return this.algorithm?.let {

                val token = this.token
                    .withIssuer(this@JWTTokenBuilder.issuer)
                    .sign(it)

                JWTTokenPart(
                    token = token,
                    expiresAt = this.expiresAt
                )
            } ?: throw ReasonCodeException(ReasonCode.ALGORITHM_NOT_DEFINED)
        }
    }
}

