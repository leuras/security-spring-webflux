package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.data.repository.AddressRepository
import com.mercadolivre.challenge.data.repository.BankDetailRepository
import com.mercadolivre.challenge.data.repository.CustomerRepository
import com.mercadolivre.challenge.entity.Customer
import com.mercadolivre.challenge.thirdparty.repository.SupplierIntegrationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomerImporterService(
    private val supplierIntegrator: SupplierIntegrationRepository,
    private val customerRepository: CustomerRepository,
    private val bankDetailRepository: BankDetailRepository,
    private val addressRepository: AddressRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerImporterService::class.java)
    }

    suspend fun importAll() {
        val customers = supplierIntegrator.getAllCustomers()

        log.info("Starting persistence worker")
        customers.forEach {
            this.persist(it)
        }
        log.info("Persistence worker completed!")
    }

    @Transactional
    private suspend fun persist(customer: Customer) {
        try {
            customerRepository.save(customer)
            bankDetailRepository.save(customer)
            addressRepository.save(customer)
        } catch (t: Throwable) {
            log.error("[FAIL]] --- Fail to import data from supplier for external id ${customer.externalId} due to: ${t.message}")
        }
    }
}