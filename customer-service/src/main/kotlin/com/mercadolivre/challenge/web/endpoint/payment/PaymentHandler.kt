package com.mercadolivre.challenge.web.endpoint.payment

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class PaymentHandler {

    fun getPaymentInfo(request: ServerRequest): Mono<ServerResponse> {
        val customerReference = request.pathVariable("customerReference")

        return ServerResponse.ok()
            .bodyValue("Payment info for customer $customerReference")
    }
}