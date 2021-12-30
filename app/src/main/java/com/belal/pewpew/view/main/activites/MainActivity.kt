package com.belal.pewpew.view.main.activites

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.belal.pewpew.R
import com.belal.pewpew.databinding.ActivityMainBinding
import com.belal.pewpew.view.main.*
import com.belal.pewpew.view.main.fragments.*
import com.belal.pewpew.view.main.viewmodels.AllDoneViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor

class MainActivity : AppCompatActivity() {
    private val allDoneViewModel: AllDoneViewModel by viewModels()
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.settingButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, binding.settingButton)
            popupMenu.menuInflater.inflate(R.menu.settingmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    //to sign out from the account
                    R.id.signout -> {
                        val alertDialog = AlertDialog
                            .Builder(this, R.style.AlertDialogTheme)
                            .setTitle("Sign Out")
                            .setMessage("Are you sure you want to Sign Out")
                        alertDialog.setPositiveButton("Yes") { _, _ ->
                            sharedPref =
                                this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("state", false)
                            sharedPrefEditor.commit()
                            FirebaseAuth.getInstance().signOut()
                            Toast.makeText(this@MainActivity, "signed out", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        alertDialog.setNegativeButton("No") { dialog, _ ->
                            dialog.cancel()
                        }

                        alertDialog.create().show()

                    }
                    // to navigate the user to the about fragment  wherever he is in the app
                    R.id.about -> {
                        val fragment =
                            navHostFragment.childFragmentManager.primaryNavigationFragment
                        when (fragment) {
                            is MainFragment -> navController.navigate(R.id.action_mainFragment_to_aboutFragment)
                            is CartFragment -> navController.navigate(R.id.action_cartFrgament_to_about)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_about)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_about)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_about)
                            is DescriptionFragment -> navController.navigate(R.id.action_descriptionFragment_to_about)
                            is OrderHistoryFragment -> navController.navigate(R.id.action_orderHistoryFragment_to_about)
                            is AllDoneFragment -> navController.navigate(R.id.action_allDoneFragment_to_about)
                            is SpecificOrderFragment -> navController.navigate(R.id.action_specificOrderFragment_to_about)

                        }
                        Log.d("belal", "onCreate: ${fragment.toString()}")
                    }
                    // to navigate the user to the orderhistory fragment  wherever he is in the app
                    R.id.orderhistory -> {
                        val fragment =
                            navHostFragment.childFragmentManager.primaryNavigationFragment
                        when (fragment) {
                            is MainFragment -> navController.navigate(R.id.action_mainFragment_to_orderHistoryFragment)
                            is CartFragment -> navController.navigate(R.id.action_cartFrgament_to_orderHistoryFragment)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_orderHistoryFragment)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_orderHistoryFragment)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_orderHistoryFragment)
                            is DescriptionFragment -> navController.navigate(R.id.action_descriptionFragment_to_orderHistoryFragment)
                            is AboutFragment -> navController.navigate(R.id.action_about_to_orderHistoryFragment)
                            is AllDoneFragment -> navController.navigate(R.id.action_allDoneFragment_to_orderHistoryFragment)
                            is SpecificOrderFragment -> navController.navigate(R.id.action_specificOrderFragment_to_orderHistoryFragment)

                        }
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
// to observe the remaining time in the alldone fragment and then notify the user that the order is done
        allDoneViewModel.timeLiveData.observe(this, {
            it
            it?.let {
                handler = Handler()
                handler.postDelayed({
                    notifi()
                    allDoneViewModel.timeLiveData.postValue(null)
                }, it.toLong())
            }
        })

    }


    fun notifi() {
        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.logopewpew)
                .setContentTitle("All Done!!")
                .setContentText("Your PewPew Order is Ready ")
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.logopewpew))

        } else {
            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.logopewpew)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.logopewpew))
        }
        allDoneViewModel.timeLiveData.postValue(null)
        notificationManager.notify(1234, builder.build())
    }
}
