package br.com.leuras.business.exception

import org.springframework.security.core.AuthenticationException

class BearerTokenAuthenticationException(message: String?) : AuthenticationException(message)