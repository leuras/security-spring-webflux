package br.com.leuras.web.endpoint

import br.com.leuras.business.extension.bearerToken
import br.com.leuras.business.extension.toResponse
import br.com.leuras.business.service.ClientCredentialService
import br.com.leuras.business.service.JWTService
import br.com.leuras.entity.ReasonCode
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class AuthorizationServerHandler(
    private val jwtIssuerService: JWTService,
    private val clientCredentialService: ClientCredentialService
) {

    private val log = LoggerFactory.getLogger(AuthorizationServerHandler::class.java)

    suspend fun login(request: ServerRequest): ServerResponse {
        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val response = this.jwtIssuerService.issue()

            ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(response)

        } catch (t: Throwable) {
            log.error("Failed to generate JWT access token due to: ${t.message}")

            val reason = ReasonCode.GENERIC_ERROR

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }

    suspend fun refreshAccessToken(request: ServerRequest): ServerResponse {
        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val principal = request
                .bearerToken()
                ?.let { this.jwtIssuerService.decodeToken(it) }
                ?.let { this.clientCredentialService.findByUsername(it.subject) }
                ?.cast(User::class.java)
                ?.awaitSingle()

            val response = this.jwtIssuerService.issue(principal)

            ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(response)

        } catch (t: Throwable) {
            log.error("Failed to refresh JWT access token due to: ${t.message}")
            ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
        }
    }
}