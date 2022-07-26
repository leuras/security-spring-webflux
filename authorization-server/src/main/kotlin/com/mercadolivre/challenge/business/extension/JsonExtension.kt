package com.mercadolivre.challenge.business.extension

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json

fun Json?.toList(objectMapper: ObjectMapper): List<String> {
    return this?.let {
        val typeRef = object : TypeReference<List<String>>() {}
        objectMapper.readValue(this.asString(), typeRef)
    } ?: emptyList()
}