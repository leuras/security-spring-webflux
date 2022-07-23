package com.mercadolivre.challenge.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    val customerReference: String = UUID.randomUUID().toString(),
    @JsonProperty("id")
    val externalId: String,
    @JsonProperty("avatar")
    val avatarUrl: String,
    @JsonProperty("fec_birthday")
    val birthday: LocalDate,
    @JsonProperty("cuenta_numero")
    val accountNumber: String,
    @JsonProperty("credit_card_num")
    val creditCardNumber: String,
    @JsonProperty("direccion")
    val address: String,
    @JsonProperty("codigo_zip")
    val zipCode: String,
    @JsonProperty("geo_latitud")
    val lat: Double,
    @JsonProperty("geo_longitud")
    val lng: Double,
    @JsonProperty("color_favorito")
    val favoriteColor: String,
    @JsonProperty("auto")
    val car: String,
    @JsonProperty("auto_modelo")
    val carModel: String,
    @JsonProperty("auto_tipo")
    val carType: String,
    @JsonProperty("auto_color")
    val carColor: String,
    @JsonProperty("cantidad_compras_realizadas")
    val purchasesMade: Long,
    @JsonProperty("fec_alta")
    val fecAlta: LocalDateTime
)
