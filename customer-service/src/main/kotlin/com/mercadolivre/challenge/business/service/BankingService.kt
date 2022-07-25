package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.business.exception.ReasonCodeException
import com.mercadolivre.challenge.data.repository.BankingRepository
import com.mercadolivre.challenge.entity.ReasonCode
import org.springframework.stereotype.Service

@Service
class BankingService(
    private val bankingRepository: BankingRepository
) {

    suspend fun getBy(customerReference: String) =
        this.bankingRepository.find(customerReference)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_BANKING_DATA_NOT_FOUND)
}