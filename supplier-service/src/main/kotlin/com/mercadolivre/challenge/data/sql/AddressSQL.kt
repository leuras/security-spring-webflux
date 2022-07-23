package com.mercadolivre.challenge.data.sql

object AddressSQL {
    private val COLUMNS = """
        customer_reference,
        address,
        zip_code,
        region,
        created_at,
        updated_at
    """.trimIndent()

    val INSERT = """
        INSERT INTO address (${COLUMNS}) 
        VALUES (
            :customer_reference, 
            :address, 
            :zip_code,
            :region,
            :created_at,
            NULL
        ) ON CONFLICT (customer_reference) DO 
            UPDATE SET (
                address,
                zip_code,
                region,
                updated_at
            ) = ( 
                :address, 
                :zip_code,
                :region,
                NOW()
            ); 
    """.trimIndent()
}