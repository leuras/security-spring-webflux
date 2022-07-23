package com.mercadolivre.challenge.thirdparty.repository

import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.entity.Customer
import com.mercadolivre.challenge.entity.ReasonCode
import com.mercadolivre.challenge.thirdparty.web.WebClientHttpConfigurator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody
import kotlin.streams.toList

@Component
class SupplierRestIntegrationRepository(
    @Value("\${integration.supplier.url}") private val url: String,
    @Value("\${integration.supplier.connection-timeout}") connectionTimeout: Long,
    private val webClient: WebClient = WebClientHttpConfigurator(url = url, connectionTimeout = connectionTimeout).configure()
) : SupplierIntegrationRepository {

    companion object {
        private val log = LoggerFactory.getLogger(SupplierRestIntegrationRepository::class.java)
    }

    override suspend fun getAllCustomers(): List<Customer> {
        return try {
            val endpointUrl = "/api/v1/usuarios"
            log.info("Fetching customers from external supplier service. [HTTP GET]--- ${this.url}${endpointUrl}")

            this.webClient.get()
                .uri(endpointUrl)
                .retrieve()
                .awaitBody<List<Customer>>()
                .also {
                    log.info("A total of ${it.size} customer(s) were fetched from the supplier service.")
                }
        } catch (e: WebClientResponseException) {
            val reason = when (e.statusCode) {
                HttpStatus.NOT_FOUND,
                HttpStatus.NO_CONTENT -> {
                    ReasonCode.IMPORTER_NO_CUSTOMERS
                }
                else -> ReasonCode.IMPORTER_INTEGRATION_FAILURE
            }

            throw ReasonCodeException(reason, e)
        } catch (e: DecodingException) {
            throw ReasonCodeException(ReasonCode.IMPORTER_WRONG_ENTITY_MAPPING, e)
        }
    }
}