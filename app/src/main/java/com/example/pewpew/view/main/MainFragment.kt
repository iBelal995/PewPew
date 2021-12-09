package com.example.pewpew.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.All.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_allFragment)
        }
        binding.Burgers.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_burgersFragment)
        }
        binding.SideOrder.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sideOrderFragment)
        }
    }

}

