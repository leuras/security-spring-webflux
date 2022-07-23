package com.mercadolivre.challenge.service

import com.mercadolivre.challenge.business.service.CustomerImporterService
import com.mercadolivre.challenge.data.repository.AddressRepository
import com.mercadolivre.challenge.data.repository.BankDetailRepository
import com.mercadolivre.challenge.data.repository.CustomerRepository
import com.mercadolivre.challenge.entity.Customer
import com.mercadolivre.challenge.fixture.CustomerFixture
import com.mercadolivre.challenge.thirdparty.repository.SupplierIntegrationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
class CustomerImporterServiceTest {

    @InjectMockKs
    private lateinit var customerImportService: CustomerImporterService

    @MockK
    private lateinit var supplierIntegrator: SupplierIntegrationRepository

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bankDetailRepository: BankDetailRepository

    @MockK
    private lateinit var addressRepository: AddressRepository

    @Test
    fun `Should successfully persist a list of customers`() = runTest {
        val costumers = listOf(
            CustomerFixture.createEntity(),
            CustomerFixture.createEntity()
        )

        coEvery { supplierIntegrator.getAllCustomers() } returns costumers
        coEvery { customerRepository.save(any()) } just runs
        coEvery { bankDetailRepository.save(any()) } just runs
        coEvery { addressRepository.save(any()) } just runs

        customerImportService.importAll()

        coVerify(exactly = 1) { supplierIntegrator.getAllCustomers() }
        coVerify(exactly = costumers.size) { customerRepository.save(any()) }
        coVerify(exactly = costumers.size) { bankDetailRepository.save(any()) }
        coVerify(exactly = costumers.size) { addressRepository.save(any()) }
    }

    @Test
    fun `Should keep iterating customers list when some persistence fails`() = runTest {
        val costumers = listOf(
            CustomerFixture.createEntity(externalId = "1"),
            CustomerFixture.createEntity(externalId = "2"),
            CustomerFixture.createEntity(externalId = "3"),
            CustomerFixture.createEntity(externalId = "4"),
            CustomerFixture.createEntity(externalId = "5")
        )

        coEvery { supplierIntegrator.getAllCustomers() } returns costumers

        coEvery { customerRepository.save(any()) } answers {
            val customer = firstArg<Customer>()
            if (customer.externalId == "2") {
                throw Exception()
            }
        }
        coEvery { bankDetailRepository.save(any()) } just runs
        coEvery { addressRepository.save(any()) } just runs

        customerImportService.importAll()

        coVerify(exactly = 1) { supplierIntegrator.getAllCustomers() }
        coVerify(exactly = 5) { customerRepository.save(any()) }
        coVerify(exactly = 4) { bankDetailRepository.save(any()) }
        coVerify(exactly = 4) { addressRepository.save(any()) }
    }
}