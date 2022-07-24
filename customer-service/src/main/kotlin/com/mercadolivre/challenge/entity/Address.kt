package com.mercadolivre.challenge.entity

import com.fasterxml.jackson.annotation.JsonView
import com.mercadolivre.challenge.entity.views.CustomerViews
import java.time.Instant

@JsonView(CustomerViews.Detailed::class)
data class Address(
    val customerReference: String,
    val address: String,
    val zipCode: String,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)
