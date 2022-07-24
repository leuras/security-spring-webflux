package com.mercadolivre.challenge.entity

import java.time.Instant

data class AddressCoordinates(
    val customerReference: String,
    val lat: Double,
    val lng: Double,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)
