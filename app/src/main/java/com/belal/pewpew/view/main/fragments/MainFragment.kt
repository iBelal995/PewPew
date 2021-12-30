package com.belal.pewpew.view.main.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentMainBinding
import com.belal.pewpew.model.menumodel.MenuModelItem
import com.belal.pewpew.view.main.viewmodels.DescriptionViewModel


class MainFragment : Fragment() {
    val bundle = Bundle()
    private lateinit var binding: FragmentMainBinding
    private val dViewModel: DescriptionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle.putBoolean("type", true)
        /*to make a double back press and let the user know that he pressed the back button
        after confirm the back with double click it will exit the app */
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
        binding.All.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_allFragment)
        }
        binding.Burgers.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_burgersFragment)
        }
        binding.SideOrder.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sideOrderFragment)
        }
        binding.littleoldfashImage.setOnClickListener {

            dViewModel.selectedItemId.postValue(
                MenuModelItem(
                    "100 grams of one delicious super black angus beef patties, classic American cheese, pickles, lettuce, ketchup, mayo, tomato, onion, little bit of house sauce, inside a freshly baked bun.",
                    "4",
                    "https://firebasestorage.googleapis.com/v0/b/pew-pew-1e87c.appspot.com/o/b4.jpeg?alt=media&token=de378561-f322-4926-b7f8-db5de07b4364",
                    "Little Old fashioned",
                    32,
                    "burger"
                )
            )
            findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
        }
        binding.oldfashburgerImage.setOnClickListener {
            dViewModel.selectedItemId.postValue(
                MenuModelItem(
                    "200 grams of two delicious super black angus beef patties, classic American cheese, pickles, lettuce, ketchup, mayo, tomato, onion, little bit of house sauce, inside a freshly baked bun.",
                    "3",
                    "https://firebasestorage.googleapis.com/v0/b/pew-pew-1e87c.appspot.com/o/b2.jpeg?alt=media&token=de378561-f322-4926-b7f8-db5de07b4364",
                    "Old fashioned",
                    42,
                    "burger"
                )
            )
            findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
        }
        binding.pewpewburgerimage.setOnClickListener {
            dViewModel.selectedItemId.postValue(
                MenuModelItem(
                    "200 grams of two delicious super black angus beef patties, classic American cheese, one lonely pickle, thin onion slices, house sauce, inside a freshly baked bun.",
                    "2",
                    "https://firebasestorage.googleapis.com/v0/b/pew-pew-1e87c.appspot.com/o/b1.jpeg?alt=media&token=de378561-f322-4926-b7f8-db5de07b4364",
                    "Pew Pew Burger",
                    40,
                    "burger"
                )
            )
            findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
        }
    }

}

