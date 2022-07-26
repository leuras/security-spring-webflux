package com.mercadolivre.challenge.entity

import org.springframework.http.HttpStatus
import java.io.Serializable

enum class ReasonCode (val code: String, val status: HttpStatus, val description: String) : Serializable {
    GENERIC_ERROR(code = "01", HttpStatus.INTERNAL_SERVER_ERROR, description = "The application has encountered an unknown error. Please try again later or contact the administrator.")
}