package com.belal.pewpew.util

import android.util.Patterns
import java.util.regex.Pattern

class RegisterValdiation {
    /**
     * Regular Expressions are a fundamental part of almost every programming language and Kotlin is no exception to it.
     * In Kotlin, the support for regular expression is provided through Regex class.
     * An object of this class represents a regular expression, that can be used for string matching purposes.
     * */
 fun validPassword(passwordText:String): String? {
    if (passwordText.length < 8) {
        return "Minimum 8 Character Password"
    }
    if (!passwordText.matches(".*[A-Z].*".toRegex())) {
        return "Must Contain 1 Upper-case Character"
    }
    if (!passwordText.matches(".*[a-z].*".toRegex())) {
        return "Must Contain 1 Lower-case Character"
    }
    if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
        return "Must Contain 1 Special Character (@#\$%^&+=)"
    }

    return null
}

     fun validconfirmPassword(passwordText: String): String? {
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }
    /**
     * matches() – This function returns a boolean indicating whether the input string completely matches the pattern or not.
     * */
     fun validEmail(emailText:String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }
}