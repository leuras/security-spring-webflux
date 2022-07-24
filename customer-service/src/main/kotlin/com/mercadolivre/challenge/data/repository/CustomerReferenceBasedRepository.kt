package com.mercadolivre.challenge.data.repository

interface CustomerReferenceBasedRepository<T> {
    suspend fun find(customerReference: String): T
}