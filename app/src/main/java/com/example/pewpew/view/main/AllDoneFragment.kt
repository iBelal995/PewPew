package com.example.pewpew.view.main

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.count.setText("seconds remaining: " + millisUntilFinished / 1000)

            }

            override fun onFinish() {
                binding.textView26.setText("All Done !! Your food is ready ")
                binding.textView26.visibility = View.VISIBLE
                binding.donegif.visibility = View.VISIBLE
                binding.w8gif.visibility = View.INVISIBLE
            }
        }
        timer.start()
    }
}