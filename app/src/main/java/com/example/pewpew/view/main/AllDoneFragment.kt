package com.example.pewpew.view.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAboutBinding
import com.example.pewpew.databinding.FragmentAllDoneBinding
import com.example.pewpew.view.MainActivity
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
        notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
       time()
    }
    fun time(){
         timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                binding.count.setText("Time remaining: ${remainingTime / 1000}s")

            }
            override fun onFinish() {
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
        timer.cancel()
        allDoneViewModel.timeLiveData.postValue(remainingTime.toInt())



    }


    }
