package com.belal.pewpew.view.main.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentOrderHistoryBinding
import com.belal.pewpew.model.HistoryModel

private lateinit var historyFragmentAdapter: HistoryRecyclerViewAdapter
private const val TAG = "HistoryFragment"

class OrderHistoryFragment : Fragment() {
    private var historyList = listOf<HistoryModel>()
    private lateinit var binding: FragmentOrderHistoryBinding
    private val historyViewModel: OrderHistoryViewModel by activityViewModels()


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
        binding.recyclerViewHistory.adapter = historyFragmentAdapter
        //to call the order history
        historyViewModel.callHistory()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_orderHistoryFragment_to_mainFragment)
                }
            })
    }

    fun observers() {
        historyViewModel.historyLiveData.observe(viewLifecycleOwner, {
            binding.progressBarHistory.animate().alpha(0f).duration = 1000
            historyFragmentAdapter.submitList(it)
            historyList = it
            Log.d(TAG, it.toString())
            binding.recyclerViewHistory.animate().alpha(1f)
        })

        historyViewModel.historyErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG, it)
                historyViewModel.historyErrorLiveData.postValue(null)

            }
        })

    }
}
