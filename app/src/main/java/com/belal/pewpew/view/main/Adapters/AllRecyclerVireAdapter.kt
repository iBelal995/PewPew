package com.belal.pewpew.view.main.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belal.pewpew.R
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.menumodel.MenuModelItem
import com.belal.pewpew.view.main.AllFragmentViewModel
import com.belal.pewpew.view.main.DescriptionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

/***
Once you've determined your layout, you need to implement your Adapter and ViewHolder.
These two classes work together to define how your data is displayed.
The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
The Adapter creates ViewHolder objects as needed, and also sets the data for those views.
The process of associating views to their data is called binding.
When you define your adapter, you need to override three key methods:
onCreateViewHolder()
onBindViewHolder()
getItemCount()
 **/
const val TAG = "ADAPTERALL"
lateinit var cartModel: CartModel
class AllRecyclerVireAdapter(val viewModel: AllFragmentViewModel, val dviewModel: DescriptionViewModel, val context: Context) :
    RecyclerView.Adapter<AllRecyclerVireAdapter.AllViewHolder>() {
    /**
     * DiffUtil is a utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one.
     * It can be used to calculate updates for a RecyclerView Adapter.
    Most of the time our list changes completely and we set new list to RecyclerView Adapter.
    And we call notifyDataSetChanged to update adapter. NotifyDataSetChanged is costly.
    DiffUtil class solves that problem now. It does its job perfectly!
     * */

    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<MenuModelItem>() {
        override fun areItemsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)
    /**
     * onCreateViewHolder(): RecyclerView calls this method whenever it needs to create a new ViewHolder.
    The method creates and initializes the ViewHolder and its associated View,
    but does not fill in the view's contents—the ViewHolder has not yet been bound to specific data.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllRecyclerVireAdapter.AllViewHolder {
        return AllViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout_menu,
                parent,
                false
            )
        )
    }

    /**
     * onBindViewHolder(): RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view holder's layout.
    For example, if the RecyclerView displays a list of names,
    the method might find the appropriate name in the list and fill in the view holder's TextView widget.
     */
    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val item = differ.currentList[position]
        Log.d(TAG, item.name)
        var count = 1

        holder.titleTextView.text = item.name
        holder.priceTextView.text = "${item.price * count} SR"
        holder.addButton.setOnClickListener {

            viewModel.addToCart(item.toCartModel(count))
            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
        }
        Picasso.get().load(item.image).into(holder.itemImageView)
        holder.itemImageView.setOnClickListener {
            dviewModel.selectedItemId.postValue(item)
            it.findNavController().navigate(R.id.action_allFragment_to_descriptionFragment)
        }

        holder.quantity.text = count.toString()

        holder.increaseButton.setOnClickListener {
            count++
            holder.quantity.text = count.toString()
            holder.priceTextView.text = "${item.price * count} SR"
        }
        holder.decreaseButton.setOnClickListener {
            if (count > 1) {
                count--
                holder.quantity.text = count.toString()
                holder.priceTextView.text = "${item.price * count} SR"
            }

        }


    }

    /**
     * getItemCount(): RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.
     */
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<MenuModelItem>) {
        differ.submitList(list)
    }

    class AllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.ordernumber)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemimageViews)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val increaseButton: Button = itemView.findViewById(R.id.increase)
        val decreaseButton: Button = itemView.findViewById(R.id.decrease)
        val quantity: TextView = itemView.findViewById(R.id.integer_number)
    }
    //Using Kotlin extension Function to Convert object of a Data Class to another Data Class object
    fun MenuModelItem.toCartModel(count: Int)=CartModel(
         description = description,
         id = id,
         image = image,
         name = name ,
         price = count*price ,
        userid = "${FirebaseAuth.getInstance().currentUser?.uid}",
        count = count
    )
}