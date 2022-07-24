package com.mercadolivre.challenge.data.repository

import com.mercadolivre.challenge.data.sql.AddressSQL
import com.mercadolivre.challenge.entity.Address
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.function.BiFunction

@Repository
class AddressRepository(
    private val databaseClient: DatabaseClient
) : CustomerReferenceBasedRepository<Address?>, BiFunction<Row, Any, Address> {

    override suspend fun find(customerReference: String): Address? {
        return databaseClient.sql(AddressSQL.SELECT_BY_CUSTOMER_REFERENCE)
            .bind("customer_reference", customerReference)
            .map(this::apply)
            .awaitSingleOrNull()
    }

    override fun apply(row: Row, p1: Any) = Address(
        customerReference = row.get("customer_reference").toString(),
        address = row.get("address").toString(),
        zipCode = row.get("zip_code").toString(),
        createdAt = row.get("created_at", Instant::class.java)!!,
        updatedAt = row.get("updated_at", Instant::class.java)
    )
}