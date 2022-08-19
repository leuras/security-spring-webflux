package br.com.leuras.web.config

import br.com.leuras.business.extension.toResponse
import br.com.leuras.entity.ReasonCode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class BasicAuthEntryPoint : ServerAuthenticationEntryPoint {
    
    private val log = LoggerFactory.getLogger(BasicAuthEntryPoint::class.java)
    
    override fun commence(exchange: ServerWebExchange, e: AuthenticationException): Mono<Void> {
        log.error("[AUTH FAILED] --- Authentication failed due to: ${e.message}")
        
        val error = ObjectMapper().writeValueAsBytes(ReasonCode.AUTHENTICATION_FAILED.toResponse())
        val response: ServerHttpResponse = exchange.response
        val body = response.bufferFactory().wrap(error)
        
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON_VALUE
        
        return response.writeWith(Mono.just(body))
    }
}