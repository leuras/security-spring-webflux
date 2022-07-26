package com.mercadolivre.challenge.web.config

import com.mercadolivre.challenge.business.service.ClientCredentialService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
class BasicAuthenticationWebFilterConfiguration(
    private val userDetailsService: ClientCredentialService
) {

    @Bean
    fun customBasicAuthenticationFilter() = AuthenticationWebFilter(
        this.createReactiveAuthenticationManager()
    )

    private fun createReactiveAuthenticationManager() =
        UserDetailsRepositoryReactiveAuthenticationManager(this.userDetailsService)
}
