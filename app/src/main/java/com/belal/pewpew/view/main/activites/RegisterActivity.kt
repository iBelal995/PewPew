package com.belal.pewpew.view.main.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.belal.pewpew.R
import com.belal.pewpew.databinding.ActivityRegisterBinding
import com.belal.pewpew.util.RegisterValdiation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var registerValdiation = RegisterValdiation()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        window.navigationBarColor =
            this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system
        binding.loginTextView.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        registerValdiation.validconfirmPassword(binding.confirmpasswordEditText.text.toString())
        binding.registerButton.setOnClickListener { submitForm() }
    }

    private fun submitForm() {
        binding.emailContainer.helperText =
            registerValdiation.validEmail(binding.emailEditText.text.toString())
        binding.passwordContainer.helperText =
            registerValdiation.validPassword(binding.passwordEditText.text.toString())
        binding.cofirmpasswordContainer.helperText =
            registerValdiation.validconfirmPassword(binding.confirmpasswordEditText.text.toString())

// put an initial value equals to  null
        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validconfirmPassword = binding.cofirmpasswordContainer.helperText == null
        val passwordXText = binding.confirmpasswordEditText.text.toString()
        val passwordText = binding.passwordEditText.text.toString()
        val email = binding.emailEditText.text.toString()
// check if the confirm password and the password are equal then check if all the text field are fulfilled
        if (passwordXText == passwordText) {
            if (validEmail && validPassword && validconfirmPassword) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, passwordText)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG)
                                .show()

                            //Navigate to Main Acitivty
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.putExtra("UserId", firebaseUser.uid)
                            intent.putExtra("Email", firebaseUser.email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this, task.exception!!.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            } else {
                invalidForm()
            }
        } else {
            Toast.makeText(this, "password doesn't match", Toast.LENGTH_SHORT).show()
        }
    }

    // check if the email and the password are correctly written
    private fun invalidForm() {
        var message = ""
        if (binding.emailContainer.helperText != null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\n\nPassword: " + binding.passwordContainer.helperText


    }


    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText =
                    registerValdiation.validEmail(binding.emailEditText.text.toString())
            }
        }
    }


    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText =
                    registerValdiation.validPassword(binding.passwordEditText.text.toString())
            }
        }
    }


}