package com.mercadolivre.challenge.business.service

import com.mercadolivre.challenge.business.extension.toAuthorities
import com.mercadolivre.challenge.data.repository.ClientCredentialRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ClientCredentialService(
    private val clientCredentialRepository: ClientCredentialRepository
) : ReactiveUserDetailsService{

    private val log = LoggerFactory.getLogger(ClientCredentialService::class.java)

    override fun findByUsername(clientId: String) =
        this.clientCredentialRepository.find(clientId)
            ?.flatMap {
                log.info("Trying to get the credentials for $clientId")

                val principal = User
                    .withUsername(it.clientId)
                    .password(it.clientSecret)
                    .authorities(it.roles.toAuthorities())
                    .build()

                Mono.just(principal)
            }!!
}
