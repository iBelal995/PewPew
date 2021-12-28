package com.belal.pewpew.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentOrderHistoryBinding
import com.belal.pewpew.databinding.FragmentProfileBinding
import com.belal.pewpew.databinding.FragmentSideOrderBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val emailAddress = FirebaseAuth.getInstance().currentUser?.email
        binding.userIdTextview.text = "User ID: ${userId}"
        binding.emailAddressTextview.text = "User Email: ${emailAddress }"
    }
}