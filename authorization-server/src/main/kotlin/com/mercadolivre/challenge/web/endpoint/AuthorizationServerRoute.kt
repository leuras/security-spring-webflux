package com.mercadolivre.challenge.web.endpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthorizationServerRoute {

    @Bean
    fun authorizationServerEndpoints(handler: AuthorizationServerHandler) = coRouter {
        path("/login").nest {
            POST("", handler::login)
        }
    }
}