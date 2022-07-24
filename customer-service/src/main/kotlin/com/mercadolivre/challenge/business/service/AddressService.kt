package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.data.repository.AddressCoordinatesRepository
import com.mercadolivre.challenge.data.repository.AddressRepository
import com.mercadolivre.challenge.entity.ReasonCode
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository,
    private val coordinatesRepository: AddressCoordinatesRepository
) {

    suspend fun getBy(customerReference: String) =
        this.addressRepository.find(customerReference)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_ADDRESS_NOT_FOUND)

    suspend fun getCoordinatesBy(customerReference: String) =
        this.coordinatesRepository.find(customerReference)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_ADDRESS_NOT_FOUND)

}