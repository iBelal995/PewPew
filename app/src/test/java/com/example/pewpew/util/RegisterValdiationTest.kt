package com.example.pewpew.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValdiationTest{
    private lateinit var validator : RegisterValdiation

    @Before
    fun setup(){
        validator = RegisterValdiation()
    }
    @Test
    fun passwordIsValidWithNotHavingUpperCasePasswordThenReturnErrorMessage() {

        val validation = validator.validPassword("qazwsxed")

        assertEquals("Must Contain 1 Upper-case Character", validation)
    }

    @Test
    fun passwordIsValidWithValidPasswordThenReturnNull() {

        val validation = validator.validPassword("Tu@12345678")
        assertEquals(null, validation)
    }
    @Test
    fun passwordIsValidWithLessThan8CharacterPasswordThenReturnErrorMessage() {

        val validation = validator.validconfirmPassword("23")

        assertEquals("Minimum 8 Character Password", validation)
    }

    @Test
    fun `passwordIsValidWithNotHavingLowerCaseCharacterPasswordThenReturnErrorMessage`() {

        val validation = validator.validconfirmPassword("QAZWSXED")
        assertEquals("Must Contain 1 Lower-case Character", validation)
    }
    @Test
    fun passwordIsValidWithNotHavingaSpecialCharacterPasswordThenReturnErrorMessage() {

        val validation = validator.validconfirmPassword("Qqazwdw1")
        assertEquals("Must Contain 1 Special Character (@#\$%^&+=)", validation)
    }
}