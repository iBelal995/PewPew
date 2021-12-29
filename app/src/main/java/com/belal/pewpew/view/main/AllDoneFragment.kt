package com.belal.pewpew.view.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentAboutBinding
import com.belal.pewpew.databinding.FragmentAllDoneBinding
import com.belal.pewpew.view.MainActivity
import com.google.android.material.navigation.NavigationView

class AllDoneFragment : Fragment() {
    private val allDoneViewModel: AllDoneViewModel by activityViewModels()
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    private lateinit var timer:CountDownTimer
      var remainingTime:Long = 0
    private lateinit var binding: FragmentAllDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDoneBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       time()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            private var doubleBackToExitPressedOnce = false
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    requireActivity().finish()
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(requireActivity(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
        })

        }
    fun time(){
        //Schedule a countdown until a time in the future, with regular notifications on intervals along the way.
         timer = object: CountDownTimer(10000, 1000) {
             /*
             The calls to onTick(long) are synchronized to this object so that one call to onTick(long) won't ever occur before
              the previous callback is complete. This is only relevant when the implementation of onTick(long) takes an amount of time to
               execute that is significant compared to the countdown interval.*/
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                binding.count.setText("Time remaining: ${remainingTime / 1000}s")

            }
            override fun onFinish() {
                //Callback fired when the time is up.
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.animate().alpha(0f).duration=1000
                binding.textView26.setText("All Done !! Your food is ready ")
                binding.textView26.visibility = View.VISIBLE
                binding.donegif.visibility = View.VISIBLE
                binding.w8gif.visibility = View.INVISIBLE
                binding.yourordertextview.visibility = View.GONE
                binding.count.visibility = View.GONE
                binding.orderagain.visibility = View.VISIBLE
                binding.vieworderhistory.visibility = View.VISIBLE
                binding.vieworderhistory.setOnClickListener {
                    findNavController().navigate(R.id.action_allDoneFragment_to_orderHistoryFragment)
                }
                binding.orderagain.setOnClickListener {
                    findNavController().navigate(R.id.action_allDoneFragment_to_mainFragment)
                }
                allDoneViewModel.timeLiveData.postValue(0)

            }
        }
        timer.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        //Cancel the countdown.
        timer.cancel()
//        allDoneViewModel.timeLiveData.postValue(remainingTime.toInt())



    }


    }
