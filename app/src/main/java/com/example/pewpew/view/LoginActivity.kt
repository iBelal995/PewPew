package com.example.firebaseauthantication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pewpew.R
import com.example.pewpew.view.MainActivity
import com.google.firebase.auth.FirebaseAuth


//private lateinit var sharedPref: SharedPreferences
//private lateinit var sharedPrefEditor: SharedPreferences.Editor
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



        RegisterTextView.setOnClickListener(){
            startActivity(Intent(this, RegisterActivity:: class.java))
            finish()
        }


        loginButton.setOnClickListener(){
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){
                            task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "User Logged In  Successfully", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this,MainActivity::class.java)
                            intent.putExtra("UserId",FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)

//                            sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
//                            sharedPrefEditor = sharedPref.edit()
//                            sharedPrefEditor.putString("firebasekey",FirebaseAuth.getInstance().currentUser!!.uid)
//                            sharedPrefEditor.commit()

                            startActivity(intent)
                            finish()
                        } else{
                            Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }else {
                Toast.makeText(this, "Please Enter the Email & password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}