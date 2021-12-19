package com.example.pewpew.view.main.Adaptersimport

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pewpew.R
import com.example.pewpew.model.CartModel
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.Adapters.TAG
import com.example.pewpew.view.main.BurgersFragmentViewModel
import com.example.pewpew.view.main.DescriptionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class BurgersRecyclerViewAdapter(val viewModel: BurgersFragmentViewModel, val dviewModel: DescriptionViewModel,val context: Context) :
    RecyclerView.Adapter<BurgersRecyclerViewAdapter.BurgersViewHolder>() {

    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<MenuModelItem>() {
        override fun areItemsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BurgersRecyclerViewAdapter.BurgersViewHolder {
        return BurgersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout_menu,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BurgersViewHolder, position: Int) {
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
            it.findNavController().navigate(R.id.action_burgersFragment_to_descriptionFragment)
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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<MenuModelItem>) {
        differ.submitList(list)
    }

    class BurgersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.ordernumber)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemimageViews)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val increaseButton: Button = itemView.findViewById(R.id.increase)
        val decreaseButton: Button = itemView.findViewById(R.id.decrease)
        val quantity: TextView = itemView.findViewById(R.id.integer_number)
    }
    fun MenuModelItem.toCartModel(count: Int)= CartModel(
        description = description,
        id = id,
        image = image,
        name = name ,
        price = count*price ,
        userid = "${FirebaseAuth.getInstance().currentUser?.uid}",
        count = count
    )
}