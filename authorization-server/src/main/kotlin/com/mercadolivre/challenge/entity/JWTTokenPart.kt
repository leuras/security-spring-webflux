package com.mercadolivre.challenge.entity

import java.time.Instant

data class JWTTokenPart(
    val token: String,
    val expiresAt: Instant
)