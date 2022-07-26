package com.mercadolivre.challenge.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivre.challenge.business.extension.toList
import com.mercadolivre.challenge.data.sql.ClientCredentialSQL
import com.mercadolivre.challenge.entity.ClientCredentialStatus
import com.mercadolivre.challenge.entity.ClientCredentials
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.function.BiFunction

@Repository
class ClientCredentialRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper
) : BiFunction<Row, Any, ClientCredentials> {

    fun find(clientId: String): Mono<ClientCredentials>? {
        return databaseClient.sql(ClientCredentialSQL.SELECT)
            .bind("client_id", clientId)
            .map(this::apply)
            .one()
    }

    override fun apply(row: Row, p1: Any) = ClientCredentials(
        clientId = row.get("client_id").toString(),
        clientSecret = row.get("client_secret").toString(),
        roles = row.get("roles", Json::class.java).toList(objectMapper),
        status = ClientCredentialStatus.valueOf(row.get("status").toString()),
        createdAt = row.get("created_at", Instant::class.java)!!,
        updatedAt = row.get("updated_at", Instant::class.java)
    )

}