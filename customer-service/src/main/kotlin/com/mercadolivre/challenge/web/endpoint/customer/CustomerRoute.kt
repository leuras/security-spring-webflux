package com.mercadolivre.challenge.web.endpoint.customer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CustomerRoute {

    companion object {
        const val API_PATH = "/api/v1/customers"
    }

    @Bean
    fun customerEndpoints(handler: CustomerHandler) = coRouter {
            path(API_PATH).nest {
                GET("/{customerReference}", handler::getCustomer)
                GET("/{customerReference}/details", handler::getCustomerDetail)
            }
        }
}