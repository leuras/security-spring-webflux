package br.com.leuras.business.service

import br.com.leuras.business.builder.JWTTokenBuilder
import br.com.leuras.business.exception.ReasonCodeException
import br.com.leuras.entity.JWTTokenResponse
import br.com.leuras.entity.ReasonCode
import br.com.leuras.web.config.JWTProperties
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class JWTService(private val config: JWTProperties) {

    suspend fun issue(principal: User? = null): JWTTokenResponse {
        val algorithmAT = Algorithm.HMAC256(this.config.accessToken.secret)
        val algorithmRT = Algorithm.HMAC256(this.config.refreshToken.secret)

        return JWTTokenBuilder(principal ?: this.getPrincipal())
            .accessToken()
            .withAlgorithm(algorithmAT)
            .expiresAfter(this.config.accessToken.expiration)
            .and()
            .refreshToken()
            .withAlgorithm(algorithmRT)
            .expiresAfter(this.config.refreshToken.expiration)
            .and()
            .withDefaultIssuer()
            .build()
    }

    suspend fun decodeToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(this.config.refreshToken.secret)

        return JWT.require(algorithm)
            .build()
            .verify(token)
            .takeIf { JWTTokenBuilder.ISSUER == it.issuer }
            ?: throw ReasonCodeException(ReasonCode.ISSUER_NOT_RECOGNIZED)
    }

    private suspend fun getPrincipal() =
        ReactiveSecurityContextHolder.getContext()
            .map { context -> context.authentication.principal }
            .cast(User::class.java)
            .awaitSingle()
}
