package com.mercadolivre.challenge.data.sql

object BankDetailSQL {
    private val COLUMNS = """
        customer_reference,
        account_number,
        credit_card_number,
        created_at,
        updated_at
    """.trimIndent()

    val SELECT_BY_CUSTOMER_REFERENCE = """
        SELECT $COLUMNS FROM bank_detail
        WHERE customer_reference = :customer_reference 
    """.trimIndent()
}