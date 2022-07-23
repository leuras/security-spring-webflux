package com.mercadolivre.challenge.business.extension

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionTest {

    @Test
    fun `Should successfully mask sensitive information on the given range`() {
        val actual = "5430577542064".mask(5, 12)
        val expected = "54305********"

        assertEquals(actual, expected)
    }

    @Test
    fun `Should successfully mask until the end when the 'to' parameter isn't given`() {
        val actual = "0123456789".mask(6)
        val expected = "012345****"

        assertEquals(actual, expected)
    }

    @Test
    fun `Should successfully mask using a specific character`() {
        val actual = "0123456789".mask(6, char = "#")
        val expected = "012345####"

        assertEquals(actual, expected)
    }

    @Test
    fun `Should not replace characters used for format the information`() {
        val actual = "6767-5481-7164-0776".mask(5)
        val expected = "6767-****-****-****"

        assertEquals(actual, expected)
    }
}