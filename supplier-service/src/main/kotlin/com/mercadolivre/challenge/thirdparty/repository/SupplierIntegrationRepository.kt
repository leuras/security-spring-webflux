package com.mercadolivre.challenge.thirdparty.repository

import com.mercadolivre.challenge.entity.Customer

interface SupplierIntegrationRepository {
    suspend fun getAllCustomers(): List<Customer>
}