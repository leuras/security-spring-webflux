package com.mercadolivre.challenge.data.sql

object AddressSQL {
    private val COLUMNS = """
        customer_reference,
        address,
        zip_code,
        created_at,
        updated_at
    """.trimIndent()

    val SELECT_BY_CUSTOMER_REFERENCE = """
        SELECT $COLUMNS FROM address
        WHERE customer_reference = :customer_reference 
    """.trimIndent()

    val SELECT_COORDINATES_BY_CUSTOMER_REFERENCE = """
        SELECT 
            customer_reference, region, created_at, updated_at 
        FROM address
        WHERE customer_reference = :customer_reference 
    """.trimIndent()
}