package com.belal.pewpew.view.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.belal.pewpew.databinding.FragmentAllBinding
import com.belal.pewpew.model.menumodel.MenuModelItem
import com.belal.pewpew.view.main.Adapters.AllRecyclerVireAdapter
import com.belal.pewpew.view.main.viewmodels.AllFragmentViewModel
import com.belal.pewpew.view.main.viewmodels.DescriptionViewModel


private const val TAG = "AllFragment"
class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private var menuList = listOf<MenuModelItem>()
    private val allViewModel: AllFragmentViewModel by activityViewModels()
    private val dViewModel: DescriptionViewModel by activityViewModels()
    private lateinit var allFragmentAdapter: AllRecyclerVireAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        allFragmentAdapter = AllRecyclerVireAdapter(allViewModel,dViewModel,requireContext())
        binding.recyclerViewAll.adapter= allFragmentAdapter
        //to get the menu
        allViewModel.callMenu()

    }
    /***
     * To get posted value from AllFragment view model
     * */
    fun observers(){
        allViewModel.menuLiveData.observe(viewLifecycleOwner,{
            binding.progressBarAll.animate().alpha(0f).duration=1000
            allFragmentAdapter.submitList(it)
            menuList = it
            Log.d(TAG,it.toString())
            binding.recyclerViewAll.animate().alpha(1f)
        })

        allViewModel.menuErrorLiveData.observe(viewLifecycleOwner,{
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG,it)
                allViewModel.menuErrorLiveData.postValue(null)

            }
        })

    }
}
