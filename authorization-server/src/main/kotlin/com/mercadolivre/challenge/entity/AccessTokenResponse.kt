package com.mercadolivre.challenge.entity

import java.time.Instant

data class AccessTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Instant,
    val refreshTokenExpiresAt: Instant
)
