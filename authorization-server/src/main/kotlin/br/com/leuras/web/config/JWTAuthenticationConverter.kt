package br.com.leuras.web.config

import br.com.leuras.business.extension.bearerToken
import br.com.leuras.web.bearer.BearerToken
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JWTAuthenticationConverter(
    private val properties: JWTProperties) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val secret = this.properties.accessToken.secret
        val algorithm = Algorithm.HMAC256(secret)
        
        return mono { exchange.request.bearerToken() }
            .map { token -> BearerToken(token, algorithm) }
            .map { jwt -> UsernamePasswordAuthenticationToken(jwt.clientId, jwt.token, AuthorityUtils.NO_AUTHORITIES) }
    }
}