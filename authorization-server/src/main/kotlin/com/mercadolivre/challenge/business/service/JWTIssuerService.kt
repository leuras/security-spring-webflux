package com.mercadolivre.challenge.business.service

import com.auth0.jwt.algorithms.Algorithm
import com.mercadolivre.challenge.business.builder.JWTTokenBuilder
import com.mercadolivre.challenge.entity.JWTTokenResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class JWTIssuerService(
    @Value("\${challenge.jwt.algorithm.secret}")
    private val secret: String,
    @Value("\${challenge.jwt.access-token-expiration}")
    private val accessTokenExpiration: Long,
    @Value("\${challenge.jwt.refresh-token-expiration}")
    private val refreshTokenExpiration: Long
) {

    suspend fun issue(): JWTTokenResponse {
        val principal = this.getPrincipal()
        val algorithm = Algorithm.HMAC256(this.secret)

        return JWTTokenBuilder(principal)
            .accessToken()
            .withDefaultIssuer()
            .expiresAfter(this.accessTokenExpiration)
            .withRoles()
            .and()
            .refreshToken()
            .withDefaultIssuer()
            .expiresAfter(this.refreshTokenExpiration)
            .and()
            .withAlgorithm(algorithm)
            .build()
    }

    private suspend fun getPrincipal() =
        ReactiveSecurityContextHolder.getContext()
            .map { context -> context.authentication.principal }
            .cast(User::class.java)
            .awaitSingle()
}
