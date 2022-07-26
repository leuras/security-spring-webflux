package br.com.leuras.entity

import java.time.Instant

data class JWTTokenResponse(
    val accessToken: String,
    val expiresAt: Instant,
    val refreshToken: String,
    val refreshTokenExpiresAt: Instant
)
