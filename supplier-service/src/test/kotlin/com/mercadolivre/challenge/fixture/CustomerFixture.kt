package com.mercadolivre.challenge.fixture

import com.mercadolivre.challenge.entity.Customer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

object CustomerFixture {

    fun createEntity(
        customerReference: String = UUID.randomUUID().toString(),
        externalId: String = "1",
        avatarUrl: String = "https://cdn.fakercloud.com/avatars/yigitpinarbasi_128.jpg",
        birthday: LocalDate = LocalDate.now(),
        accountNumber: String = "38047405",
        creditCardNumber: String = "3731-257378-42633",
        address: String = "Shaniya Springs",
        zipCode: String = "41351",
        lat: Double = 10.3752,
        lng: Double = -105.7502,
        favoriteColor: String = "gold",
        car: String = "Volkswagen Cruze",
        carModel: String = "Model S",
        carType: String = "Hatchback",
        carColor: String = "Jaguar Camaro",
        purchasesMade: Long = 68331,
        fecAlta: LocalDateTime = LocalDateTime.now()
    ) = Customer(
        customerReference = customerReference,
        externalId = externalId,
        avatarUrl = avatarUrl,
        birthday = birthday,
        accountNumber = accountNumber,
        creditCardNumber = creditCardNumber,
        address = address,
        zipCode = zipCode,
        lat = lat,
        lng = lng,
        favoriteColor = favoriteColor,
        car = car,
        carModel = carModel,
        carType = carType,
        carColor = carColor,
        purchasesMade = purchasesMade,
        fecAlta = fecAlta
    )
}