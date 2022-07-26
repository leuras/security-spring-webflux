package com.mercadolivre.challenge.business.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.mercadolivre.challenge.entity.AccessTokenResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.stream.Collectors

@Service
class JWTIssuerService(
    @Value("\${challenge.jwt.algorithm.secret}")
    secret: String,
    private val algorithm: Algorithm = Algorithm.HMAC256(secret),
    @Value("\${challenge.jwt.access-token-expiration}")
    private val accessTokenExpiration: Long,
    @Value("\${challenge.jwt.refresh-token-expiration}")
    private val refreshTokenExpiration: Long
) {

    companion object {
        private const val ROLES = "roles"
        private const val ISSUER = "authorization-server.mercadolivre.com"
    }

    suspend fun issue(): AccessTokenResponse {
        val user = this.getPrincipal()

        val accessTokenExpiresAt  = Instant.now().plusMillis(this.accessTokenExpiration)
        val refreshTokenExpiresAt = Instant.now().plusMillis(this.refreshTokenExpiration)

        val accessToken = this.withJWTBuilder(accessTokenExpiresAt)
            .withClaim(ROLES, user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(this.algorithm)

        val refreshToken = this.withJWTBuilder(refreshTokenExpiresAt)
            .sign(this.algorithm)

        return AccessTokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt
        )
    }

    suspend fun withJWTBuilder(expiresAt: Instant): JWTCreator.Builder {
        val user = this.getPrincipal()

        return JWT.create()
            .withSubject(user.username)
            .withExpiresAt(expiresAt)
            .withIssuer(ISSUER)
    }

    private suspend fun getPrincipal() =
        ReactiveSecurityContextHolder.getContext()
            .map { context -> context.authentication.principal }
            .cast(User::class.java)
            .awaitSingle()
}