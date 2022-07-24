package com.mercadolivre.challenge.web.endpoint.address

import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.business.extension.toResponse
import com.mercadolivre.challenge.business.service.AddressService
import com.mercadolivre.challenge.entity.ReasonCode
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AddressHandler(private val addressService: AddressService) {

    companion object {
        private val log = LoggerFactory.getLogger(AddressHandler::class.java)
    }

    suspend fun getCustomerAddress(request: ServerRequest): ServerResponse {
        val customerReference = request.pathVariable("customerReference")

        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val address = this.addressService.getBy(customerReference = customerReference)

            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(address)

        } catch (t: Throwable) {
            val reason = when(t) {
                is ReasonCodeException -> t.reasonCode
                else -> ReasonCode.GENERIC_ERROR
            }

            log.error("Failed to get customer address for $customerReference due to: ${t.message}")

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }

    suspend fun getCoordinates(request: ServerRequest): ServerResponse {
        val customerReference = request.pathVariable("customerReference")

        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val coordinates = this.addressService
                .getCoordinatesBy(customerReference = customerReference)

            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(coordinates)

        } catch (t: Throwable) {
            val reason = when(t) {
                is ReasonCodeException -> t.reasonCode
                else -> ReasonCode.GENERIC_ERROR
            }

            log.error("Failed to get address coordinates for $customerReference due to: ${t.message}")

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }
}