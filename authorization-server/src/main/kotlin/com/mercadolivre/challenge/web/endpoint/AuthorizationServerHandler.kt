package com.mercadolivre.challenge.web.endpoint

import com.mercadolivre.challenge.business.extension.toResponse
import com.mercadolivre.challenge.business.service.JWTIssuerService
import com.mercadolivre.challenge.entity.ReasonCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AuthorizationServerHandler(
    private val jwtIssuerService: JWTIssuerService
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
}