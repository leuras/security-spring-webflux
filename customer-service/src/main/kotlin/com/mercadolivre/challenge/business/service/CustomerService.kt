package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.data.repository.CustomerRepository
import com.mercadolivre.challenge.entity.Customer
import com.mercadolivre.challenge.entity.ReasonCode
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressService: AddressService
) {

    suspend fun getBy(customerReference: String) =
        this.customerRepository.find(customerReference)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_NOT_FOUND)

    suspend fun getDetailedBy(customerReference: String): Customer {
        val customer = this.customerRepository.find(customerReference)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_NOT_FOUND)

        return customer.copy(address = addressService.getBy(customerReference))
    }
}