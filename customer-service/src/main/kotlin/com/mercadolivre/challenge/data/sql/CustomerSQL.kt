package com.mercadolivre.challenge.data.sql

object CustomerSQL {
    private val COLUMNS = """
        customer_reference,
        external_id,
        avatar_url,
        birthday,
        metadata::jsonb,
        created_at,
        updated_at
    """.trimIndent()

    val SELECT_BY_CUSTOMER_REFERENCE = """
        SELECT $COLUMNS FROM customer
        WHERE customer_reference = :customer_reference 
    """.trimIndent()
}