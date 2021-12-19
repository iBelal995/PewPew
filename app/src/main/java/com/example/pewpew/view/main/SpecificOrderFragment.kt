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
import com.example.pewpew.databinding.FragmentSideOrderBinding
import com.example.pewpew.databinding.FragmentSpecificOrderBinding
import com.example.pewpew.model.HistoryModel
import com.example.pewpew.view.main.Adaptersimport.CartRecyclerViewAdapter
import com.example.pewpew.view.main.Adaptersimport.HistoryRecyclerViewAdapter
import com.example.pewpew.view.main.Adaptersimport.SpecificOrderRecyclerViewAdapter

private const val TAG = "specificOrderideOrderFragment"

class SpecificOrderFragment : Fragment() {
    val bundle = Bundle()
    private lateinit var binding: FragmentSpecificOrderBinding
    private lateinit var specificFragmentAdapter: SpecificOrderRecyclerViewAdapter
    private val historyViewModel: OrderHistoryViewModel by activityViewModels()
    private var historyList = listOf<HistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificOrderBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()

        specificFragmentAdapter = SpecificOrderRecyclerViewAdapter()
        binding.recyclerViewSpecificOrder.adapter= specificFragmentAdapter
        var ordernumber = requireArguments().getInt("ordernumber",0)
        historyViewModel.getHistoryspec(ordernumber)
    }
    fun observers(){
        historyViewModel.historyxlLiveData.observe(viewLifecycleOwner,{
            it?.let {
                binding.progressBarspecificorder.animate().alpha(0f).duration=1000
                specificFragmentAdapter.submitList(it)
                historyList = it
                Log.d(TAG,it.toString())
                binding.recyclerViewSpecificOrder.animate().alpha(1f)
                historyViewModel.historyxlLiveData.postValue(null)
            }
        })

        historyViewModel.historyErrorLiveData.observe(viewLifecycleOwner,{
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG,it)
                historyViewModel.historyErrorLiveData.postValue(null)

            }
        })

    }
}