package com.belal.pewpew.view.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.belal.pewpew.R
import com.belal.pewpew.databinding.FragmentCartBinding
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import com.belal.pewpew.model.menumodel.MenuModelItem
import com.belal.pewpew.view.main.Adaptersimport.CartRecyclerViewAdapter
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.properties.Delegates


private const val TAG = "CartFragment"

class CartFragment : Fragment() {
     var autogenerate = (1..1000000).random()
    var totalAmount:Double = 0.0
    private var cartList = listOf<CartModel>()
    private val cartViewModel: CartFreagmentViewModel by activityViewModels()
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
            binding.progressBarCart.animate().alpha(0f)
            cartFragmentAdapter.submitList(it)
            cartList = it
            binding.confirmorder.setOnClickListener {

                if(cartList.size > 0){

                    for(item in cartList) {
                        cartViewModel.addToHistory(item.toHistoryModel())

                        cartViewModel.removeFromCart(item.id)
                    }
                    findNavController().navigate(R.id.action_cartFrgament_to_allDoneFragment)
                }
                Toast.makeText(requireActivity(), "Your order is sent", Toast.LENGTH_SHORT).show()
            }
//            Log.d("CART LIST", cartList.size.toString())
            if (cartList.size > 0){ /***/
                binding.yourcartempty.visibility = View.GONE
                binding.confirmorder.visibility = View.VISIBLE
                binding.totalprice.visibility = View.VISIBLE
            }else
            {
                binding.confirmorder.visibility = View.INVISIBLE
                binding.totalprice.visibility = View.INVISIBLE
                binding.yourcartempty.visibility = View.VISIBLE
            }

            var price = 0.0
            for(item in cartList){
                 price = price + item.price.toInt()
            }
             totalAmount = price + price*(0.15).toDouble()
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
    fun CartModel.toHistoryModel()= HistoryModel(
        description = description,
        id = id,
        image = image,
        name = name ,
        price = price ,
        userid = "${FirebaseAuth.getInstance().currentUser?.uid}",
        count = count,
        originalprice = 0,
        date = Date(),
        ordernumber = autogenerate,
        totalprice = totalAmount


    )

}