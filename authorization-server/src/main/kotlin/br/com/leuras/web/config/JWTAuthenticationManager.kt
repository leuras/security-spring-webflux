package br.com.leuras.web.config

import br.com.leuras.business.exception.BearerTokenAuthenticationException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class JWTAuthenticationManager : ReactiveAuthenticationManager {
    
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .onErrorMap { e -> BearerTokenAuthenticationException(e.message) }
    }
}