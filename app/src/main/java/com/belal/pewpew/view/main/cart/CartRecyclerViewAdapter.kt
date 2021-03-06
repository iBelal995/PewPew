package com.belal.pewpew.view.main.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belal.pewpew.R
import com.belal.pewpew.model.CartModel
import com.squareup.picasso.Picasso

class CartRecyclerViewAdapter(val viewModel: CartFreagmentViewModel) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {
    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<CartModel>() {
        override fun areItemsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout_cart,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = differ.currentList[position]
        var count = item.count
        var orignalprice = item.price / count
        holder.titleTextView.text = item.name
        holder.priceTextView.text = "${item.price} SR"
        Picasso.get().load(item.image).into(holder.itemImageView)
        holder.quantity.text = item.count.toString()

        //to delete an item from the cart i use alertDialog to prevent user mistakes
        holder.deleteButton.setOnClickListener {
            val alertDialog = AlertDialog
                .Builder(holder.itemView.context, R.style.AlertDialogTheme)
                .setTitle("Delete ${item.name.uppercase()}")
                .setMessage("Are you sure you want to delete this item?")
            alertDialog.setPositiveButton("Yes") { _, _ ->

                viewModel.removeFromCart(item.id)

                val ayhaga = mutableListOf<CartModel>()
                ayhaga.addAll(differ.currentList)
                ayhaga.remove(item)
                differ.submitList(ayhaga)
            }
            alertDialog.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            alertDialog.create().show()

        }
        holder.increaseButton.setOnClickListener {
            count++
            holder.quantity.text = count.toString()
            holder.priceTextView.text = "${orignalprice * count} SR"
            item.price = orignalprice * count
            item.count = count
            viewModel.updateCart(item)

        }
        holder.decreaseButton.setOnClickListener {
            if (count > 1) {
                count--
                holder.quantity.text = count.toString()
                holder.priceTextView.text = "${orignalprice * count} SR"
                item.price = orignalprice * count
                item.count = count
                viewModel.updateCart(item)


            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CartModel>) {
        differ.submitList(list)
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.ordernumber)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemimageViews)
        val deleteButton: Button = itemView.findViewById(R.id.deleteItemButton)
        val increaseButton: Button = itemView.findViewById(R.id.increase)
        val decreaseButton: Button = itemView.findViewById(R.id.decrease)
        val quantity: TextView = itemView.findViewById(R.id.integer_number)
    }
}