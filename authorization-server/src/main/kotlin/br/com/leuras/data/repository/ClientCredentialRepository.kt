package br.com.leuras.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import br.com.leuras.business.extension.toList
import br.com.leuras.data.sql.ClientCredentialSQL
import br.com.leuras.entity.ClientCredentialStatus
import br.com.leuras.entity.ClientCredentials
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
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
        description = row.get("description").toString(),
        roles = row.get("roles", Json::class.java).toList(objectMapper),
        status = ClientCredentialStatus.valueOf(row.get("status").toString()),
        createdAt = row.get("created_at", Instant::class.java)!!,
        updatedAt = row.get("updated_at", Instant::class.java)
    )
}
