package com.mercadolivre.challenge.data.sql

object BankDetailSQL {
    private val COLUMNS = """
        customer_reference,
        account_number,
        credit_card_number,
        created_at,
        updated_at
    """.trimIndent()

    val INSERT = """
        INSERT INTO bank_detail (${COLUMNS}) 
        VALUES (
            :customer_reference, 
            :account_number, 
            :credit_card_number,
            :created_at,
            NULL
        ) ON CONFLICT (customer_reference) DO 
            UPDATE SET (
                account_number, 
                credit_card_number,
                updated_at
            ) = ( 
                :account_number, 
                :credit_card_number, 
                NOW()
            ); 
    """.trimIndent()
}