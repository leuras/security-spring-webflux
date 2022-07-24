package com.mercadolivre.challenge.web.endpoint.customer

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class CustomerHandler {

    fun getCustomer(request: ServerRequest): Mono<ServerResponse> {
        val customerReference = request.pathVariable("customerReference")

        return ServerResponse.ok()
            .bodyValue("Customer $customerReference")
    }

    fun getCustomerDetail(request: ServerRequest): Mono<ServerResponse> {
        val customerReference = request.pathVariable("customerReference")

        return ServerResponse.ok()
            .bodyValue("Details for customer $customerReference")
    }
}