package com.example.pewpew.view.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pewpew.R
import com.example.pewpew.databinding.FragmentOrderHistoryBinding
import com.example.pewpew.model.HistoryModel
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adaptersimport.CartRecyclerViewAdapter
import com.example.pewpew.view.main.Adaptersimport.HistoryRecyclerViewAdapter

private lateinit var historyFragmentAdapter: HistoryRecyclerViewAdapter
private const val TAG = "HistoryFragment"

class OrderHistoryFragment : Fragment() {
    private var historyList = listOf<HistoryModel>()
    private lateinit var binding: FragmentOrderHistoryBinding
    private val historyViewModel: OrderHistoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        historyFragmentAdapter = HistoryRecyclerViewAdapter(historyViewModel)
        binding.recyclerViewHistory.adapter= historyFragmentAdapter
        historyViewModel.callHistory()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            private var doubleBackToExitPressedOnce = false
            override fun handleOnBackPressed() {
             findNavController().navigate(R.id.action_orderHistoryFragment_to_mainFragment)
            }
        })
    }
    fun observers(){
        historyViewModel.historyLiveData.observe(viewLifecycleOwner,{
            binding.progressBarHistory.animate().alpha(0f).duration=1000
            historyFragmentAdapter.submitList(it)
            historyList = it
            Log.d(TAG,it.toString())
            binding.recyclerViewHistory.animate().alpha(1f)
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
