package com.mercadolivre.challenge.job.runner

import com.mercadolivre.challenge.business.service.CustomerImporterService
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomerImporterRunner(
    private val customerImporter: CustomerImporterService
) : CommandLineRunner {

    companion object {
        private val log = LoggerFactory.getLogger(CustomerImporterRunner::class.java)
    }

    override fun run(vararg args: String?) {
        log.info("Starting customer importer runner")
        runBlocking {
            customerImporter.importAll()
        }
        log.info("Stopping customer importer runner. The total time elapsed was ")
    }
}