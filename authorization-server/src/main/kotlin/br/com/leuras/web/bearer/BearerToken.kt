package br.com.leuras.web.bearer

import br.com.leuras.business.builder.JWTTokenBuilder
import br.com.leuras.business.exception.ReasonCodeException
import br.com.leuras.entity.ReasonCode
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT

class BearerToken(token: String, algorithm: Algorithm, issuer: String = JWTTokenBuilder.DEFAULT_ISSUER) {
    
    private val jwt: DecodedJWT
    
    init {
        this.jwt = JWT.require(algorithm)
            .build()
            .verify(token)
            .takeIf { it.issuer == issuer }
            ?: throw ReasonCodeException(ReasonCode.ISSUER_NOT_RECOGNIZED)
    }
    
    val clientId: String
        get() = this.jwt.subject
    
    val token: String
        get() = this.jwt.token
}
