package com.belal.pewpew.view.main.identity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.belal.pewpew.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forget_password)
        val resetButton: Button = findViewById(R.id.resetpassbuitton)
        val resetEmail: EditText = findViewById(R.id.resetpasswordemailtext)
        resetButton.setOnClickListener {
            forgetpassword(resetEmail)
        }

    }
    // to send the reset password to the user email
    private fun forgetpassword(username: EditText) {
        if (username.text.toString().isEmpty()) {
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        Firebase.auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Reset password link have been sent to your email",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "There is no user found with this email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

}