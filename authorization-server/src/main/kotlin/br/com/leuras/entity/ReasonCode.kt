package br.com.leuras.entity

import org.springframework.http.HttpStatus
import java.io.Serializable

enum class ReasonCode (val code: String, val status: HttpStatus, val description: String) : Serializable {
    GENERIC_ERROR(code = "01", HttpStatus.INTERNAL_SERVER_ERROR, description = "The application has encountered an unknown error. Please try again later or contact the administrator."),
    AUTHENTICATION_FAILED(code = "02", HttpStatus.UNAUTHORIZED, description = "Authentication failed. Invalid, missing, or inactive credentials."),
    
    ALGORITHM_NOT_DEFINED(code = "10", HttpStatus.INTERNAL_SERVER_ERROR, description = "Algorithm not specified."),
    MALFORMED_TOKEN(code = "11", HttpStatus.INTERNAL_SERVER_ERROR, description = "Malformed or invalid token definition."),
    ISSUER_NOT_RECOGNIZED(code = "12", HttpStatus.UNAUTHORIZED, description = "The given issuer was not recognized as a trusted issuer."),
}