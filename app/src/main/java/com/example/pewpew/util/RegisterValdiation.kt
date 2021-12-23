package com.example.pewpew.util

import android.util.Patterns
import java.util.regex.Pattern

class RegisterValdiation {

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