package com.mercadolivre.challenge.data.repository

import com.mercadolivre.challenge.data.sql.AddressSQL
import com.mercadolivre.challenge.entity.AddressCoordinates
import io.r2dbc.postgresql.codec.Point
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.function.BiFunction

@Repository
class AddressCoordinatesRepository(
    private val databaseClient: DatabaseClient
) : CustomerReferenceBasedRepository<AddressCoordinates?>, BiFunction<Row, Any, AddressCoordinates> {

    override suspend fun find(customerReference: String): AddressCoordinates? {
        return databaseClient.sql(AddressSQL.SELECT_COORDINATES_BY_CUSTOMER_REFERENCE)
            .bind("customer_reference", customerReference)
            .map(this::apply)
            .awaitSingleOrNull()
    }

    override fun apply(row: Row, p1: Any): AddressCoordinates {
        val point = row.get("region", Point::class.java)!!

        return AddressCoordinates(
            customerReference = row.get("customer_reference").toString(),
            lat = point.x,
            lng = point.y,
            createdAt = row.get("created_at", Instant::class.java)!!,
            updatedAt = row.get("updated_at", Instant::class.java)
        )
    }
}