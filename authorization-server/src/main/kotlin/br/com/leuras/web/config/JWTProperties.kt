package br.com.leuras.web.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("leuras.jwt")
data class JWTProperties(
    val accessToken: JWTTokenProperties,
    val refreshToken: JWTTokenProperties
)

data class JWTTokenProperties(
    val secret: String,
    val expiration: Long
)