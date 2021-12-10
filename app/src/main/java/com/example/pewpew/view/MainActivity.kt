package com.example.pewpew.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.firebaseauthantication.LoginActivity
import com.example.firebaseauthantication.SHARED_PREF_FILE
import com.example.pewpew.R
import com.example.pewpew.databinding.ActivityMainBinding
import com.example.pewpew.view.main.*
import com.google.firebase.auth.FirebaseAuth

private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
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
                        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        sharedPrefEditor = sharedPref.edit()
                        sharedPrefEditor.putBoolean("state",false)
                        sharedPrefEditor.commit()

                        FirebaseAuth.getInstance().signOut()
                        Toast.makeText(this@MainActivity, "signed out", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    }

                    R.id.about ->{
                        val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                        when(fragment){
                            is MainFragment ->  navController.navigate(R.id.action_mainFragment_to_aboutFragment)
                            is CartFragment ->  navController.navigate(R.id.action_cartFrgament_to_about)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_about)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_about)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_about)
                        }
                        Log.d("belal", "onCreate: ${fragment.toString()}")
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
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    }
