package com.mercadolivre.challenge.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivre.challenge.business.extension.toMetadata
import com.mercadolivre.challenge.data.sql.CustomerSQL
import com.mercadolivre.challenge.entity.Customer
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDate
import java.util.function.BiFunction

@Repository
class CustomerRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper
) : CustomerReferenceBasedRepository<Customer?>, BiFunction<Row, Any, Customer> {

    override suspend fun find(customerReference: String): Customer? {
        return databaseClient.sql(CustomerSQL.SELECT_BY_CUSTOMER_REFERENCE)
            .bind("customer_reference", customerReference)
            .map(this::apply)
            .awaitSingleOrNull()
    }

    override fun apply(row: Row, p1: Any) = Customer(
        customerReference = row.get("customer_reference").toString(),
        externalId = row.get("external_id").toString(),
        avatarUrl = row.get("avatar_url").toString(),
        birthday = row.get("birthday", LocalDate::class.java)!!,
        metadata = row.get("metadata", Json::class.java).toMetadata(objectMapper),
        createdAt = row.get("created_at", Instant::class.java)!!,
        updatedAt = row.get("updated_at", Instant::class.java)
    )
}