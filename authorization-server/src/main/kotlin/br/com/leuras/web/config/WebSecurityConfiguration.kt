package br.com.leuras.web.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfiguration(
    private val jwtAuthenticationFilter: AuthenticationWebFilter) {
    
    @Bean
    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(this.jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .csrf().disable()
            .formLogin().disable()
            .httpBasic()
            .authenticationEntryPoint(BasicAuthEntryPoint())
        
        return http.build()
    }
}