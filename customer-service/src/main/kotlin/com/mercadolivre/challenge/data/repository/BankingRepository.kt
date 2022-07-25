package com.mercadolivre.challenge.data.repository

import com.mercadolivre.challenge.data.sql.BankDetailSQL
import com.mercadolivre.challenge.entity.BankInfo
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.function.BiFunction

@Repository
class BankingRepository(
    private val databaseClient: DatabaseClient
) : CustomerReferenceBasedRepository<BankInfo?>, BiFunction<Row, Any, BankInfo> {

    override suspend fun find(customerReference: String): BankInfo? {
        return databaseClient.sql(BankDetailSQL.SELECT_BY_CUSTOMER_REFERENCE)
            .bind("customer_reference", customerReference)
            .map(this::apply)
            .awaitSingleOrNull()
    }

    override fun apply(row: Row, p1: Any) = BankInfo(
        customerReference = row.get("customer_reference").toString(),
        accountNumber = row.get("account_number").toString(),
        creditCardNumber = row.get("credit_card_number").toString(),
        createdAt = row.get("created_at", Instant::class.java)!!,
        updatedAt = row.get("updated_at", Instant::class.java)
    )
}