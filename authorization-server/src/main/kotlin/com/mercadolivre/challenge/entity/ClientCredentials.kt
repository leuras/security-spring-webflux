package com.mercadolivre.challenge.entity

import java.time.Instant

data class ClientCredentials(
    val clientId: String,
    val clientSecret: String,
    val roles: List<String>,
    val status: ClientCredentialStatus,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)
