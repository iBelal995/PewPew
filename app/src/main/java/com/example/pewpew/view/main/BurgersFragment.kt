package com.example.pewpew.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pewpew.databinding.FragmentBurgersBinding
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adapters.AllRecyclerVireAdapter
import com.example.pewpew.view.main.Adaptersimport.BurgersRecyclerViewAdapter

private const val TAG = "BurgersFragment"

class BurgersFragment : Fragment() {
    private var menuList = listOf<MenuModelItem>()
    private val burgersViewModel: BurgersFragmentViewModel by activityViewModels()
    private lateinit var burgersFragmentAdapter: BurgersRecyclerViewAdapter
    private lateinit var binding: FragmentBurgersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBurgersBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        burgersFragmentAdapter = BurgersRecyclerViewAdapter(burgersViewModel)
        binding.recyclerViewBurgers.adapter= burgersFragmentAdapter
        burgersViewModel.callMenu()
    }
    fun observers(){
        burgersViewModel.menuLiveData.observe(viewLifecycleOwner,{
            binding.progressBarBurgers.animate().alpha(0f).duration=1000
           burgersFragmentAdapter.submitList(it)
            menuList = it
            Log.d(TAG,it.toString())
            binding.recyclerViewBurgers.animate().alpha(1f)
        })

        burgersViewModel.menuErrorLiveData.observe(viewLifecycleOwner,{
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG,it)
                burgersViewModel.menuErrorLiveData.postValue(null)

            }
        })

    }
}