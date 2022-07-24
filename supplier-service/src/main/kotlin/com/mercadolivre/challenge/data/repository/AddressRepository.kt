package com.mercadolivre.challenge.data.repository

import com.mercadolivre.challenge.data.sql.AddressSQL
import com.mercadolivre.challenge.entity.Customer
import io.r2dbc.postgresql.codec.Point
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class AddressRepository(
    private val databaseClient: DatabaseClient
) : SQLRepository<Customer> {

    override suspend fun save(entity: Customer) {
        databaseClient.sql(AddressSQL.INSERT)
            .bind("customer_reference", entity.customerReference)
            .bind("address", entity.address)
            .bind("zip_code", entity.zipCode)
            .bind("region", Point.of(entity.lat, entity.lng))
            .bind("created_at", Instant.now())
            .await()
    }
}