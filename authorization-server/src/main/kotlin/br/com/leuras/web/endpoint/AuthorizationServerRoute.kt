package br.com.leuras.web.endpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthorizationServerRoute {

    @Bean
    fun authorizationServerEndpoints(handler: AuthorizationServerHandler) = coRouter {
        path("/").nest {
            POST("login", handler::login)
            GET("refresh-access-token", handler::refreshAccessToken)
            path("/api").nest {
                POST("/clients", handler::create)
            }
        }
    }
}