package com.example.pewpew.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAboutBinding
import com.example.pewpew.databinding.FragmentCartBinding
import android.content.Intent
import com.example.firebaseauthantication.LoginActivity
import com.example.pewpew.view.MainActivity
import com.google.firebase.auth.FirebaseAuth


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val emailAddress = FirebaseAuth.getInstance().currentUser!!.email
        binding.userIdTextview.text = "User Id: ${userId}"
        binding.emailAddressTextview.text = "User Email: ${emailAddress }"


    }



}