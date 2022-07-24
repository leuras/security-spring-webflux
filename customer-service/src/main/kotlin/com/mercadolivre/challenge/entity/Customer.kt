package com.mercadolivre.challenge.entity

import com.fasterxml.jackson.annotation.JsonView
import com.mercadolivre.challenge.entity.views.CustomerViews
import java.time.Instant
import java.time.LocalDate

@JsonView(CustomerViews.Basic::class, CustomerViews.Detailed::class)
data class Customer(
    val customerReference: String,
    val externalId: String,
    val avatarUrl: String,
    val birthday : LocalDate,
    val metadata: Map<String, Any>,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    @JsonView(CustomerViews.Detailed::class)
    val address: Address? = null
)
