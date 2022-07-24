package com.mercadolivre.challenge.web.endpoint.customer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping

@Configuration(proxyBeanMethods = false)
class CustomerRoute {

    companion object {
        val API_PATH = "/api/v1/customers"
    }

    @Bean
    fun customerEndpoints(handler: CustomerHandler): RouterFunction<ServerResponse> {
        return router {
            path(API_PATH).nest {
                GET("/{customerReference}", handler::getCustomer)
                GET("/{customerReference}/details", handler::getCustomerDetail)
            }
        }
    }
}