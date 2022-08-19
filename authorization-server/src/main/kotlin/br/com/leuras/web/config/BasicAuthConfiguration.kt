package br.com.leuras.web.config

import br.com.leuras.business.extension.toAuthorities
import br.com.leuras.data.repository.ClientCredentialRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

@Configuration
class BasicAuthConfiguration {
    
    private val log = LoggerFactory.getLogger(BasicAuthConfiguration::class.java)
    
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
    
    @Bean
    fun userDetailService(clientCredentialRepository: ClientCredentialRepository) = ReactiveUserDetailsService { clientId ->
        log.info("[AUTH] --- Trying to get the credentials for client id: $clientId")
    
        clientCredentialRepository.find(clientId)
            ?.flatMap {
                val principal = User
                    .withUsername(it.clientId)
                    .password(it.clientSecret)
                    .authorities(it.roles.toAuthorities())
                    .build()
    
                Mono.just(principal)
            }
    }
}