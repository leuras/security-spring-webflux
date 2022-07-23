package com.mercadolivre.challenge.business.extension

import com.mercadolivre.challenge.fixture.CustomerFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class CustomerExtensionTest {

    @Test
    fun `Should successfully create a customer metadata`() {
        val expectedDate = LocalDateTime.of(2022, Month.JULY, 23, 10, 0)
        val customer = CustomerFixture.createEntity(
            car = "Ferrari",
            carColor = "Red",
            carModel = "F8",
            carType = "Luxury Sports",
            favoriteColor = "Black",
            purchasesMade = 8000,
            fecAlta = expectedDate
        )

        val actualMetadata = customer.toMetadata()

        assertEquals(actualMetadata["car"], customer.car)
        assertEquals(actualMetadata["car_color"], customer.carColor)
        assertEquals(actualMetadata["car_model"], customer.carModel)
        assertEquals(actualMetadata["car_type"], customer.carType)
        assertEquals(actualMetadata["favorite_color"], customer.favoriteColor)
        assertEquals(actualMetadata["purchases_made"], customer.purchasesMade)
        assertEquals(actualMetadata["fec_alta"], customer.fecAlta)
    }
}