package com.belal.pewpew.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.belal.firebaseauthantication.LoginActivity
import com.belal.firebaseauthantication.SHARED_PREF_FILE
import com.belal.pewpew.R
import com.belal.pewpew.databinding.ActivityMainBinding
import com.belal.pewpew.view.main.*
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
        /**
         * View binding is a feature that allows you to more easily write code that interacts with views.
         * Once view binding is enabled in a module,
         * it generates a binding class for each XML layout file present in that module.
         * An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.
         * */

        /**
         * If view binding is enabled for a module,
         * a binding class is generated for each XML layout file that the module contains.
         * Each binding class contains references to the root view and all views that have an ID.
         * The name of the binding class is generated by converting the name of the XML file to Pascal case and adding the word "Binding" to the end.
         * */

        /***
         * Before starting the implementation of the Navigation Architecture Component.
        you must be aware of the following:
         * Navigation Graph (Destination and Action)
         * Navigation Host Fragment
         * Navigation Controller
         * */


        /***
         * Navigation Graph
        Navigation graph is an XML file which shows you how the Fragments are related to each other.
        It contains the Fragments Destinations, routes, arguments, action, etc.
        Using the Navigation graph you can quickly set the Transition Animation by setting up some attributes.
         * */

        /**
         * A Navigation Graph consists of:
         * Destinations: The individual screens the user can navigate to and can specify arguments,
        actions and deep link URLs to these destinations.
         * Actions: The routes user can take between your app’s destinations.
        Which are represented by the arrow sign in the Design view.
         * */


        /***
         * Navigation Host Fragment
        In our Activity layout, we have to add a fragment as NavHost and need to define, Where the NavHostFragment finds the navigation graph.
        You can see the following code in which we define the fragment as NavHostFragment and also define the navigation graph.
         * You must defines which Navigation Graph will be associated with the Navigation Host
         * */


        /***
         * Navigation Controller
        NavController is the class which deals with the FragmentManager or FragmentTransaction.
        NavController manages application navigation with the NavHostFragment.
        Navigation flow and destination are determined by the navigation graph owned by the NavController class.
        And currently running NavHostFragment directly deal with maintaining back-stack,
        action bar, toolbar, navigation drawer icon, etc
         * */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.settingButton.setOnClickListener {
             val popupMenu: PopupMenu = PopupMenu(this,binding.settingButton)
            popupMenu.menuInflater.inflate(R.menu.settingmenu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.signout ->{
                        val alertDialog = AlertDialog
                .Builder(this,R.style.AlertDialogTheme)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to Sign Out")
            alertDialog.setPositiveButton("Yes") { _, _ ->
                sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("state", false)
                sharedPrefEditor.commit()
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this@MainActivity, "signed out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }

            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

            alertDialog.create().show()

        }



                    R.id.about ->{
                        val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                        when(fragment){
                            is MainFragment ->  navController.navigate(R.id.action_mainFragment_to_aboutFragment)
                            is CartFragment ->  navController.navigate(R.id.action_cartFrgament_to_about)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_about)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_about)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_about)
                            is DescriptionFragment -> navController.navigate(R.id.action_descriptionFragment_to_about)
                            is OrderHistoryFragment -> navController.navigate(R.id.action_orderHistoryFragment_to_about)
                            is AllDoneFragment -> navController.navigate(R.id.action_allDoneFragment_to_about)
                            is SpecificOrderFragment -> navController.navigate(R.id.action_specificOrderFragment_to_about)
                            is ProfileFragment -> navController.navigate(R.id.action_profileFragment_to_about)

                        }
                        Log.d("belal", "onCreate: ${fragment.toString()}")
                    }
                    R.id.orderhistory->{
                        val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                        when(fragment){
                            is MainFragment ->  navController.navigate(R.id.action_mainFragment_to_orderHistoryFragment)
                            is CartFragment ->  navController.navigate(R.id.action_cartFrgament_to_orderHistoryFragment)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_orderHistoryFragment)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_orderHistoryFragment)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_orderHistoryFragment)
                            is DescriptionFragment -> navController.navigate(R.id.action_descriptionFragment_to_orderHistoryFragment)
                            is AboutFragment -> navController.navigate(R.id.action_about_to_orderHistoryFragment)
                            is AllDoneFragment -> navController.navigate(R.id.action_allDoneFragment_to_orderHistoryFragment)
                            is SpecificOrderFragment -> navController.navigate(R.id.action_specificOrderFragment_to_orderHistoryFragment)
                            is ProfileFragment -> navController.navigate(R.id.action_profileFragment_to_orderHistoryFragment)

                        }
                    }
                    R.id.profile->{
                        val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                        when(fragment){
                            is MainFragment ->  navController.navigate(R.id.action_mainFragment_to_profileFragment)
                            is CartFragment ->  navController.navigate(R.id.action_cartFrgament_to_profileFragment)
                            is SideOrderFragment -> navController.navigate(R.id.action_sideOrderFragment_to_profileFragment)
                            is AllFragment -> navController.navigate(R.id.action_allFragment_to_profileFragment)
                            is BurgersFragment -> navController.navigate(R.id.action_burgersFragment_to_profileFragment)
                            is DescriptionFragment -> navController.navigate(R.id.action_descriptionFragment_to_profileFragment)
                            is AboutFragment -> navController.navigate(R.id.action_about_to_profileFragment)
                            is AllDoneFragment -> navController.navigate(R.id.action_allDoneFragment_to_profileFragment)
                            is SpecificOrderFragment -> navController.navigate(R.id.action_specificOrderFragment_to_orderHistoryFragment)
                            is OrderHistoryFragment -> navController.navigate(R.id.action_orderHistoryFragment_to_profileFragment)
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

        allDoneViewModel.timeLiveData.observe(this,{ it

            it?.let {
                handler = Handler()
                handler.postDelayed({
                    notifi()
                    allDoneViewModel.timeLiveData.postValue(null)

                }, it.toLong())
            }
        })

    }
    // If you want to back to the last fragment from where you come here just user the navigateUp method of NavController

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    fun notifi (){
        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.GREEN
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
