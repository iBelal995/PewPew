package com.belal.pewpew.view.main.Adaptersimport

import com.belal.pewpew.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belal.pewpew.model.HistoryModel
import com.squareup.picasso.Picasso

class SpecificOrderRecyclerViewAdapter() :
    RecyclerView.Adapter<SpecificOrderRecyclerViewAdapter.SpecificOrderViewHolder>() {
    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<HistoryModel>() {
        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALL_BACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificOrderRecyclerViewAdapter.SpecificOrderViewHolder {
        return SpecificOrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout_orderitems,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecificOrderViewHolder, position: Int) {
        val item = differ.currentList[position]
        Picasso.get().load(item.image).into(holder.itemImageView)
        holder.quantity.text = item.count.toString()
        holder.titleTextView.text = item.name
        holder.priceTextView.text = "${item.price}SR"

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<HistoryModel>) {
        differ.submitList(list)}

    class SpecificOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.namespecific)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextViewspecific)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemimageViewspecific)
        val quantity: TextView = itemView.findViewById(R.id.integer_numberspecific)
    }
}