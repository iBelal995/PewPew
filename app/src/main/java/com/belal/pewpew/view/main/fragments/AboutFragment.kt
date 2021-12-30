package com.belal.pewpew.view.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.belal.pewpew.databinding.FragmentAboutBinding
import com.google.firebase.auth.FirebaseAuth


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationButton.setOnClickListener {
            // to open google map app and lead to  the restaurant location
            val gmapsIntentURI =
                Uri.parse("https://www.google.com/maps/place/Pew+Pew+%D8%B7%D8%AE+%D8%B7%D8%AE%E2%80%AD/@24.7723579,46.6955491,17z/data=!3m1!4b1!4m5!3m4!1s0x3e2efd180151b4ad:0x2373d137bca7bf15!8m2!3d24.7723529!4d46.6977339")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmapsIntentURI)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}



