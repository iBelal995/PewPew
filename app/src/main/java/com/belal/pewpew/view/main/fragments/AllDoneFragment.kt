package com.belal.pewpew.view.main.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentAllDoneBinding
import com.belal.pewpew.view.main.viewmodels.AllDoneViewModel

class AllDoneFragment : Fragment() {
    private val allDoneViewModel: AllDoneViewModel by activityViewModels()
    private lateinit var timer: CountDownTimer
    var remainingTime: Long = 0
    private lateinit var binding: FragmentAllDoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        time()
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                private var doubleBackToExitPressedOnce = false
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        requireActivity().finish()
                        return
                    }

                    this.doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        requireActivity(),
                        "Please click BACK again to exit",
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        doubleBackToExitPressedOnce = false
                    }, 2000)
                }
            })

    }

    fun time() {
        //to countdown the remaining time to make the order within 10 seconds
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                //to show the remaining time
                binding.count.setText("Time remaining: ${remainingTime / 1000}s")

            }

            override fun onFinish() {
                //what will happen after the count down finish
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.animate().alpha(0f).duration = 1000
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

    // to prevent the app from crashing while the countdown running i use onDestroy fun()
    override fun onDestroy() {
        super.onDestroy()
        //Cancel the countdown.
        timer.cancel()
    }
}
