package com.mercadolivre.challenge.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivre.challenge.business.extension.toMetadata
import com.mercadolivre.challenge.data.sql.CustomerSQL
import com.mercadolivre.challenge.entity.Customer
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class CustomerRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper
) : SQLRepository<Customer>{

    override suspend fun save(entity: Customer) {
        databaseClient.sql(CustomerSQL.INSERT)
            .bind("customer_reference", entity.customerReference)
            .bind("external_id", entity.externalId)
            .bind("avatar_url", entity.avatarUrl)
            .bind("birthday", entity.birthday)
            .bind("metadata", objectMapper.writeValueAsString(entity.toMetadata()))
            .bind("created_at", Instant.now())
            .await()
    }
}