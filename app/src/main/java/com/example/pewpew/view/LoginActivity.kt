package com.example.firebaseauthantication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pewpew.R
import com.example.pewpew.view.ForgetPasswordActivity
import com.example.pewpew.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
var SHARED_PREF_FILE = "pref"
private const val TAG = "prefffff"
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.navigationBarColor =
            this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system

        val emailEditText: EditText = findViewById(R.id.emailAddress_EditText)
        val passwordEditText: EditText = findViewById(R.id.password_EditText)
        val loginButton: Button = findViewById(R.id.login_button)
        val RegisterTextView: TextView = findViewById(R.id.register_TextView)
        val resetpassword:TextView = findViewById(R.id.forgetpassword)



        RegisterTextView.setOnClickListener() {
            startActivity(Intent(this, RegisterActivity::class.java))

        }

        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("state", false)){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Log.d(TAG,sharedPref.getBoolean("state", true).toString())
        }

        loginButton.setOnClickListener() {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User Logged In  Successfully", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
                            sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("state", true)
                            sharedPrefEditor.commit()
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please Enter the Email & password", Toast.LENGTH_SHORT).show()
            }
        }
        resetpassword.setOnClickListener() {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(intent)
        }

        }
    }

