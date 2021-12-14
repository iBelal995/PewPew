package com.example.pewpew.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAboutBinding
import com.example.pewpew.databinding.FragmentAllBinding
import com.example.pewpew.databinding.FragmentDescriptionBinding
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adapters.AllRecyclerVireAdapter
import com.example.pewpew.view.main.Adaptersimport.SideOrderRecyclerViewAdapter
import com.squareup.picasso.Picasso
import java.util.Observer

private lateinit var binding: FragmentDescriptionBinding
class DescriptionFragment : Fragment() {

    private var menuList = listOf<MenuModelItem>()

    private val dViewModel: DescriptionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBardes.animate().alpha(0f).duration=1000
        dViewModel.selectedItemId.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Picasso.get().load(it.image).into(binding.itemImage)
                binding.description.setText(it.description)
            })
        binding.buttondes.setOnClickListener {
            findNavController().navigate(R.id.action_descriptionFragment_to_burgersFragment)
        }

        arguments?.let {
            binding.buttondes.visibility = View.VISIBLE
        }


    }


}