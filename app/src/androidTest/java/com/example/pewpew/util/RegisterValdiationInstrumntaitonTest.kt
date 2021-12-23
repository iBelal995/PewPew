package com.example.pewpew.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValdiationInstrumntaitonTest{
    private lateinit var validator : RegisterValdiation

    @Before
    fun setup(){
        validator = RegisterValdiation()

    }
    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseValue() {

        val validation = validator.validEmail("test-dd.com")

        assertEquals("Invalid Email Address", validation)
    }
    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue() {

        val validation = validator.validEmail("test@test.com")

        assertEquals(null, validation)
    }
}