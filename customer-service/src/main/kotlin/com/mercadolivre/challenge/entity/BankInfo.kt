package com.mercadolivre.challenge.entity

import java.time.Instant

data class BankInfo(
    val customerReference: String,
    val accountNumber: String,
    val creditCardNumber: String,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)
