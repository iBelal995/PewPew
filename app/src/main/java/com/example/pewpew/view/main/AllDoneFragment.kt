package com.example.pewpew.view.main

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAboutBinding
import com.example.pewpew.databinding.FragmentAllDoneBinding

class AllDoneFragment : Fragment() {
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
    }
    fun time(){
        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.count.setText("Time remaining: ${millisUntilFinished / 1000}s")

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
            }
        }
        timer.start()
    }


    }
