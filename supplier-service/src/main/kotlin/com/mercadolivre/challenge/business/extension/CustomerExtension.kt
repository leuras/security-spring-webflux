package com.mercadolivre.challenge.business.extension

import com.mercadolivre.challenge.entity.Customer

fun Customer.toMetadata() = mapOf(
    "favorite_color" to this.favoriteColor,
    "car" to this.car,
    "car_model" to this.carModel,
    "car_type" to this.carType,
    "car_color" to this.carColor,
    "fec_alta" to this.fecAlta,
    "purchases_made" to this.purchasesMade
)