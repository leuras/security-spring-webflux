package br.com.leuras.web.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfiguration(
    private val customBasicAuthenticationFilter: AuthenticationWebFilter
) {

    @Bean
    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST,"/login")
            .permitAll()
            .pathMatchers(HttpMethod.GET, "/refresh-access-token")
            .permitAll()

        return http.build();
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}