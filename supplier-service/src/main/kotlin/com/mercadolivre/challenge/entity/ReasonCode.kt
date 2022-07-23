package com.mercadolivre.challenge.entity

enum class ReasonCode (val code: String, val description: String) {
    IMPORTER_NO_CUSTOMERS(code = "01", description = "There's no customer data available to import from supplier partner service."),
    IMPORTER_INTEGRATION_FAILURE(code = "02", description = "Failed to integrate with the given supplier endpoint URL."),
    IMPORTER_WRONG_ENTITY_MAPPING(code = "03", description = "Failed to import data from supplier service due to wrong entity mapping JSON schema.")
}