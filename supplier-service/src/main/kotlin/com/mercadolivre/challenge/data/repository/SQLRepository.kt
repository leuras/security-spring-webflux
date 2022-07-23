package com.mercadolivre.challenge.data.repository

interface SQLRepository<T> {
    suspend fun save(entity: T)
}