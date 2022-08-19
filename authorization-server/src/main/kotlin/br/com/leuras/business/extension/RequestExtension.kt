package br.com.leuras.business.extension

import br.com.leuras.web.bearer.BearerHeader
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.reactive.function.server.ServerRequest

const val BEARER_PREFIX = "Bearer"

fun ServerRequest.bearerToken() = BearerHeader { this.headers().firstHeader(HttpHeaders.AUTHORIZATION) }.token()

fun ServerHttpRequest.bearerToken() = BearerHeader { this.headers.getFirst(HttpHeaders.AUTHORIZATION) }.token()

fun BearerHeader.token() = this.content()
        ?.takeIf { it.startsWith(BEARER_PREFIX, ignoreCase = true) }
        ?.replace(BEARER_PREFIX, "", ignoreCase = true)
        ?.trim()
