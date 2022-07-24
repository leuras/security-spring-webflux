package com.mercadolivre.challenge.web.endpoint.payment

import com.mercadolivre.challenge.web.endpoint.customer.CustomerRoute
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration(proxyBeanMethods = false)
class PaymentRoute {

    companion object {
        val API_PATH = "${CustomerRoute.API_PATH}/{customerReference}/payments"
    }

    @Bean
    fun paymentEndpoints(handler: PaymentHandler): RouterFunction<ServerResponse> {
        return router {
            path(API_PATH).nest {
                GET("/details", handler::getPaymentInfo)
            }
        }
    }
}