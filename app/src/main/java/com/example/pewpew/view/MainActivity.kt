package com.example.pewpew.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.firebaseauthantication.LoginActivity
import com.example.pewpew.R
import com.example.pewpew.databinding.ActivityMainBinding
import com.example.pewpew.view.main.AboutFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavView,navController)


        binding.settingButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,binding.settingButton)
            popupMenu.menuInflater.inflate(R.menu.settingmenu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.signout ->{
                        FirebaseAuth.getInstance().signOut()

                    startActivity(Intent(this, LoginActivity::class.java))}

                    R.id.about ->{


                        navController.navigate(R.id.action_mainFragment_to_aboutFragment)
                    }

                }
                true
            })
            popupMenu.show()
        }



        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor =
                this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.fragment,fr)
        frag.commit()
    }
    }
