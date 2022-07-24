package com.mercadolivre.challenge.web.endpoint.address

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AddressHandler {

    fun getCustomerAddress(request: ServerRequest): Mono<ServerResponse> {
        val customerReference = request.pathVariable("customerReference")

        return ServerResponse.ok()
            .bodyValue("Address of Customer $customerReference")
    }

    fun getCoordinates(request: ServerRequest): Mono<ServerResponse> {
        val customerReference = request.pathVariable("customerReference")

        return ServerResponse.ok()
            .bodyValue("Coordinates of Customer's address $customerReference")
    }
}