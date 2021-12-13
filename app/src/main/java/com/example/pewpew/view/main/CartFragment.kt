package com.example.pewpew.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pewpew.databinding.FragmentCartBinding
import com.example.pewpew.model.CartModel
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adapters.AllRecyclerVireAdapter
import com.example.pewpew.view.main.Adapters.cartModel
import com.example.pewpew.view.main.Adaptersimport.CartRecyclerViewAdapter

private const val TAG = "CartFragment"

class CartFragment : Fragment() {
    private var cartList = listOf<CartModel>()
    private val cartViewModel: CartFreagmentViewModel by activityViewModels()
    private val dViewModel: DescriptionViewModel by activityViewModels()
    private lateinit var cartFragmentAdapter: CartRecyclerViewAdapter
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        cartFragmentAdapter = CartRecyclerViewAdapter(cartViewModel)
        binding.recyclerViewCart.adapter= cartFragmentAdapter
        cartViewModel.getCart()


    }
    fun observers(){


        cartViewModel.CartLiveData.observe(viewLifecycleOwner, Observer{
            binding.progressBarCart.animate().alpha(0f).duration=1000
            cartFragmentAdapter.submitList(it)
            cartList = it

            var price = 0.0
            for(item in cartList){
                 price = price + item.price.toInt()
            }
            var totalAmount = price + price*(0.15).toDouble()
            binding.totalprice.text = "Total Amount (Including VAT) : ${totalAmount.toString()} SR"

            Log.d(TAG,it.toString())
            binding.recyclerViewCart.animate().alpha(1f)
        })

        cartViewModel.CartErrorLiveData.observe(viewLifecycleOwner,{
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                Log.d(TAG,it)
                cartViewModel.CartErrorLiveData.postValue(null)

            }
        })

    }
}