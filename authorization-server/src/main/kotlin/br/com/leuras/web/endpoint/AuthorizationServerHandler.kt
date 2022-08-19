package br.com.leuras.web.endpoint

import br.com.leuras.business.extension.toResponse
import br.com.leuras.business.service.JWTService
import br.com.leuras.entity.ReasonCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitPrincipal
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class AuthorizationServerHandler(
    private val jwtService: JWTService) {

    private val log = LoggerFactory.getLogger(AuthorizationServerHandler::class.java)

    suspend fun login(request: ServerRequest): ServerResponse {
        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")
            
            val response = request.awaitPrincipal()!!
                .let { it as UsernamePasswordAuthenticationToken }
                .let { it.principal as User }
                .let { userDetails -> this.jwtService.issue(userDetails) }
            
            return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(response)
                .also { log.info("[AUTH OK] --- Successfully authenticated. JWT tokens were issued.") }
            
        } catch (t: Throwable) {
            log.error("[AUTH FAILED] --- Failed to generate JWT tokens due to: ${t.message}")

            val reason = ReasonCode.GENERIC_ERROR

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }
    
    suspend fun refreshAccessToken(request: ServerRequest): ServerResponse {
        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")
    
            ServerResponse.status(HttpStatus.CREATED)
                .bodyValueAndAwait("Token refreshed ...")

        } catch (t: Throwable) {
            log.error("Failed to refresh JWT access token due to: ${t.message}")
            ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
        }
    }
    
    suspend fun create(request: ServerRequest): ServerResponse {
        return ServerResponse.status(HttpStatus.CREATED)
            .bodyValueAndAwait("<o>")
    }
}