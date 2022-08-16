package br.com.leuras.business.extension

import org.springframework.web.reactive.function.server.ServerRequest

const val AUTHORIZATION_HEADER = "AUTHORIZATION"
const val AUTHORIZATION_HEADER_PREFIX = "Bearer"

fun ServerRequest.bearerToken() = this.headers()
        .firstHeader(AUTHORIZATION_HEADER)
        ?.takeIf { it.startsWith(AUTHORIZATION_HEADER_PREFIX, ignoreCase = true) }
        ?.replace(AUTHORIZATION_HEADER_PREFIX, "", ignoreCase = true)
        ?.trim()