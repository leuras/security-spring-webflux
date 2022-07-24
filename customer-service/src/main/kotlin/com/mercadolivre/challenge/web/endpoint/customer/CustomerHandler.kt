package com.mercadolivre.challenge.web.endpoint.customer

import com.fasterxml.jackson.annotation.JsonView
import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.business.extension.toResponse
import com.mercadolivre.challenge.business.service.CustomerService
import com.mercadolivre.challenge.entity.ReasonCode
import com.mercadolivre.challenge.entity.views.CustomerViews
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2CodecSupport
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class CustomerHandler(
    private val customerService: CustomerService
) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerHandler::class.java)
    }

    @JsonView(CustomerViews.Basic::class)
    suspend fun getCustomer(request: ServerRequest): ServerResponse {
        val customerReference = request.pathVariable("customerReference")

        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val customer = this.customerService
                .getBy(customerReference = customerReference)

            ServerResponse.ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, CustomerViews.Basic::class.java)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(customer)

        } catch (t: Throwable) {
            val reason = when(t) {
                is ReasonCodeException -> t.reasonCode
                else -> ReasonCode.GENERIC_ERROR
            }

            log.error("Failed to get customer for $customerReference due to: ${t.message}")

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }

    suspend fun getCustomerDetail(request: ServerRequest): ServerResponse {
        val customerReference = request.pathVariable("customerReference")

        return try {
            log.info("[${request.method()!!.name}] --- ${request.requestPath()}")

            val customer = this.customerService
                .getDetailedBy(customerReference = customerReference)

            ServerResponse.ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, CustomerViews.Detailed::class.java)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(customer)

        } catch (t: Throwable) {
            val reason = when(t) {
                is ReasonCodeException -> t.reasonCode
                else -> ReasonCode.GENERIC_ERROR
            }

            log.error("Failed to get detailed customer for $customerReference due to: ${t.message}")

            ServerResponse.status(reason.status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(reason.toResponse())
        }
    }
}