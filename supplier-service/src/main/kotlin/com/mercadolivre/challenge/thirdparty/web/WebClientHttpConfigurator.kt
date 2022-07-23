package com.mercadolivre.challenge.thirdparty.web

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

class WebClientHttpConfigurator(
    private val url: String,
    private val connectionTimeout: Long = 30000,
    private val readTimeout: Long = 30000,
    private val writeTimeout: Long = 30000,
    private val responseTimeout: Long = 30000
) {

    fun configure(): WebClient {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.connectionTimeout.toInt())
            .responseTimeout(Duration.ofMillis(this.responseTimeout))
            .doOnConnected { connection ->
                connection
                    .addHandlerLast(ReadTimeoutHandler(this.readTimeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(this.writeTimeout, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .baseUrl(url)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }
}
