package com.example.firebaseauthantication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pewpew.R
import com.example.pewpew.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.navigationBarColor =
            this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system


        val emailAddress: EditText = findViewById(R.id.emailAddress_EditText)
        val password: EditText = findViewById(R.id.password_EditText)
        val registerButton: Button = findViewById(R.id.register_button)
        val logintextview: TextView = findViewById(R.id.login_TextView)


        logintextview.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }

        registerButton.setOnClickListener {

            val email:String = emailAddress.text.toString()
            val password:String = password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(){
                        task ->
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this,"User Registered Successfully", Toast.LENGTH_LONG).show()

                        //Navigate to Main Acitivty
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("UserId", firebaseUser.uid)
                        intent.putExtra("Email", firebaseUser.email)
                        startActivity(intent)
                        finish()

                    } else{
                        Toast.makeText(this, task.exception!!.message.toString()
                            , Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }
}