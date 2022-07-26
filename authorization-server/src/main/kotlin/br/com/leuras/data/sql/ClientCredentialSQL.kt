package br.com.leuras.data.sql

object ClientCredentialSQL {

    private val COLUMNS = """
        client_id,
        client_secret,
        description,
        roles::json,
        status,
        created_at,
        updated_at
    """.trimIndent()

    val INSERT = """
        INSERT INTO client_credential ($COLUMNS)
        VALUES (:client_id, :client_secret, :description, :roles, :status, :created_at, NULL)
    """.trimIndent()

    val SELECT = """
        SELECT $COLUMNS FROM client_credential
        WHERE client_id = :client_id
    """.trimIndent()
}