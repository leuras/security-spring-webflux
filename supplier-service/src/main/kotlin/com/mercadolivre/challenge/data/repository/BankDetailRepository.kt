package com.mercadolivre.challenge.data.repository

import com.mercadolivre.challenge.business.extension.mask
import com.mercadolivre.challenge.data.sql.BankDetailSQL
import com.mercadolivre.challenge.entity.Customer
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class BankDetailRepository(
    private val databaseClient: DatabaseClient
) : SQLRepository<Customer> {

    override suspend fun save(entity: Customer) {
        databaseClient.sql(BankDetailSQL.INSERT)
            .bind("customer_reference", entity.customerReference)
            .bind("account_number", entity.accountNumber.mask(4))
            .bind("credit_card_number", entity.creditCardNumber.mask(0,entity.creditCardNumber.length - 5))
            .bind("created_at", Instant.now())
            .await()
    }
}