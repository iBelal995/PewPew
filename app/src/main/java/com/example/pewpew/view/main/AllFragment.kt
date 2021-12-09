package com.example.pewpew.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAllBinding
import com.example.pewpew.databinding.FragmentBurgersBinding

class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}