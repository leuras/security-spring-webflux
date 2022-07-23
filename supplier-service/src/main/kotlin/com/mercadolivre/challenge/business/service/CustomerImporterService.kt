package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.data.repository.BankDetailRepository
import com.mercadolivre.challenge.data.repository.CustomerRepository
import com.mercadolivre.challenge.thirdparty.repository.SupplierIntegrationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomerImporterService(
    private val supplierIntegrator: SupplierIntegrationRepository,
    private val customerRepository: CustomerRepository,
    private val bankDetailRepository: BankDetailRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerImporterService::class.java)
    }

    @Transactional
    suspend fun importAll() {
        val customers = supplierIntegrator.getAllCustomers()

        customers.forEach {
            customerRepository.save(it)
            bankDetailRepository.save(it)
        }
    }
}