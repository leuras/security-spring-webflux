package br.com.leuras.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
class JWTAuthConfiguration(
    private val properties: JWTProperties) {
    
    @Bean
    fun jwtAuthenticationFilter(): AuthenticationWebFilter {
        val converter = JWTAuthenticationConverter(this.properties)
        val manager = JWTAuthenticationManager()
        
        return AuthenticationWebFilter(manager).apply {
            this.setServerAuthenticationConverter(converter)
        }
    }
}
