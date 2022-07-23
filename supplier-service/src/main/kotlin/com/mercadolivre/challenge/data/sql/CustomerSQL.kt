package com.mercadolivre.challenge.data.sql

object CustomerSQL {
    private val COLUMNS = """
        customer_reference,
        external_id,
        avatar_url,
        birthday,
        metadata,
        created_at,
        updated_at
    """.trimIndent()

    val INSERT = """
        INSERT INTO customer (${COLUMNS}) 
        VALUES (
            :customer_reference, 
            :external_id, 
            :avatar_url, 
            :birthday, 
            :metadata::jsonb,
            :created_at,
            NULL
        ) ON CONFLICT (external_id) DO 
            UPDATE SET (
                avatar_url, 
                birthday, 
                metadata,
                updated_at
            ) = ( 
                :avatar_url, 
                :birthday, 
                :metadata::jsonb,
                NOW()
            ); 
    """.trimIndent()
}