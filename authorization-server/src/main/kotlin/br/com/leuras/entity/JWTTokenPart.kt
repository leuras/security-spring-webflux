package br.com.leuras.entity

import java.time.Instant

data class JWTTokenPart(
    val token: String,
    val expiresAt: Instant
)