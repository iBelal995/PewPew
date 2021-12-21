package com.example.pewpew.util

import android.util.Patterns
import java.util.regex.Pattern

class RegisterValdiation {
//    private val REGEX_PASSWORD = "^(?=.*[0-9])" +  // a digit must occur at least once
//            "(?=.*[a-z])" +  // a lower case letter must occur at least once
//            "(?=.*[A-Z])" +  // an upper case letter must occur at least once
//            "(?=.*[!@#\\$%\\^&\\*])" +  // a special character must occur at least once
//            "(?=\\S+$)" +  // no whitespace allowed in the entire string
//            ".{8,}$" // anything, at least eight places though
//    private val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
//    fun emailIsValid(email:String) : Boolean {
//        val pattern = Pattern.compile(REGEX_EMAIL)
//        val matcher = pattern.matcher(email)
//        return matcher.matches()
//    }
//
//    fun passwordIsValid(password:String):Boolean{
//        val pattern = Pattern.compile(REGEX_PASSWORD)
//        val matcher = pattern.matcher(password)
//        return matcher.matches()
//    }
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
     fun validEmail(emailText:String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }
}