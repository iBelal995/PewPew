package com.example.firebaseauthantication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pewpew.R
import com.example.pewpew.databinding.ActivityRegisterBinding
import com.example.pewpew.util.RegisterValdiation
import com.example.pewpew.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    //    private val validator = RegisterValdiation()
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
        binding.emailContainer.helperText = registerValdiation.validEmail(binding.emailEditText.text.toString())
        binding.passwordContainer.helperText = registerValdiation.validPassword(binding.passwordEditText.text.toString())
        binding.cofirmpasswordContainer.helperText = registerValdiation.validconfirmPassword(binding.confirmpasswordEditText.text.toString())


        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validconfirmPassword = binding.cofirmpasswordContainer.helperText == null
        val passwordXText = binding.confirmpasswordEditText.text.toString()
        val passwordText = binding.passwordEditText.text.toString()
        val email = binding.emailEditText.text.toString()

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
                binding.emailContainer.helperText = registerValdiation.validEmail(binding.emailEditText.text.toString())
            }
        }
    }


    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = registerValdiation.validPassword(binding.passwordEditText.text.toString())
            }
        }
    }




//        val emailAddress: EditText = findViewById(R.id.emailEditText)
//        val password: EditText = findViewById(R.id.passwordEditText)
//        val cpassword: EditText = findViewById(R.id.confirmpasswordEditText)
//        val registerButton: Button = findViewById(R.id.register_button)
//        val logintextview: TextView = findViewById(R.id.login_TextView)
//
//

//
//        registerButton.setOnClickListener {
//
//            val email:String = emailAddress.text.toString()
//            val password:String = password.text.toString()
//            val cpassword:String = cpassword.text.toString()
//
//            if(email.isNotEmpty() && password.isNotEmpty()){
//                    if (validator.emailIsValid(email)) {
//                        if (validator.passwordIsValid(password)) {
//                            if(password==cpassword){
//                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(){
//                                    task ->
//                            if(task.isSuccessful){
//                                val firebaseUser: FirebaseUser = task.result!!.user!!
//                                Toast.makeText(this,"User Registered Successfully", Toast.LENGTH_LONG).show()
//
//                                //Navigate to Main Acitivty
//                                val intent = Intent(this, LoginActivity::class.java)
//                                intent.putExtra("UserId", firebaseUser.uid)
//                                intent.putExtra("Email", firebaseUser.email)
//                                startActivity(intent)
//                                finish()
//                            } else {
//                                Toast.makeText(
//                                    this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show() }
//                        }}else{
//                                Toast.makeText(this, "Password and confirm password don't match", Toast.LENGTH_SHORT).show()
//                        }
//                        } else
//                            Toast.makeText(this, "Make sure your password is Containing at least eight places & at least one of each (Upparcase,Lowercase,[0-9],[!@#%^&*]) & no spaces allowed.", Toast.LENGTH_LONG).show()
//                    }else{
//                        Toast.makeText(this, "Make sure you typed your email address correctly.", Toast.LENGTH_SHORT).show()
//                    }
//            }else {
//                Toast.makeText(this, "Please Enter the Email & password", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//    }
}