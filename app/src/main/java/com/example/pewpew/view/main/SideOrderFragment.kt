package com.example.pewpew.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentAllBinding
import com.example.pewpew.databinding.FragmentBurgersBinding
import com.example.pewpew.databinding.FragmentSideOrderBinding
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adaptersimport.BurgersRecyclerViewAdapter
import com.example.pewpew.view.main.Adaptersimport.SideOrderRecyclerViewAdapter

private const val TAG = "SideOrderFragment"

class SideOrderFragment : Fragment() {
    private lateinit var binding: FragmentSideOrderBinding
    private var menuList = listOf<MenuModelItem>()
    private val sideOrderViewModel: SideOrderFragmentViewModel by activityViewModels()
    private val dViewModel: DescriptionViewModel by activityViewModels()
    private lateinit var sideOrderFragmentAdapter: SideOrderRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSideOrderBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        sideOrderFragmentAdapter = SideOrderRecyclerViewAdapter(sideOrderViewModel,dViewModel,requireContext())
        binding.recyclerViewSideOrder.adapter= sideOrderFragmentAdapter
        sideOrderViewModel.callMenu()
    }

    fun observers(){
        sideOrderViewModel.menuLiveData.observe(viewLifecycleOwner,{
            binding.progressBarSideOrder.animate().alpha(0f).duration=1000
            sideOrderFragmentAdapter.submitList(it)
            menuList = it
            Log.d(TAG,it.toString())
            binding.recyclerViewSideOrder.animate().alpha(1f)
        })

        sideOrderViewModel.menuErrorLiveData.observe(viewLifecycleOwner,{
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG,it)
                sideOrderViewModel.menuErrorLiveData.postValue(null)
            }
        })
    }
}