package com.mercadolivre.challenge.web.endpoint.address

import com.mercadolivre.challenge.web.endpoint.customer.CustomerRoute
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration(proxyBeanMethods = false)
class AddressRoute {

    companion object {
        val API_PATH = "${CustomerRoute.API_PATH}/{customerReference}"
    }

    @Bean
    fun addressEndpoints(handler: AddressHandler): RouterFunction<ServerResponse> {
        return router {
            path(API_PATH).nest {
                GET("/address", handler::getCustomerAddress)
                GET("/address/coordinates", handler::getCoordinates)
            }
        }
    }
}