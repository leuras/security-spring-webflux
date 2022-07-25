package com.mercadolivre.challenge.web.endpoint.banking

import com.mercadolivre.challenge.web.endpoint.customer.CustomerRoute
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration(proxyBeanMethods = false)
class BankingRoute {

    companion object {
        const val API_PATH = "${CustomerRoute.API_PATH}/{customerReference}/banking"
    }

    @Bean
    fun bankingEndpoints(handler: BankingHandler) = coRouter {
            path(API_PATH).nest {
                GET("/details", handler::getBankDetails)
            }
        }
}