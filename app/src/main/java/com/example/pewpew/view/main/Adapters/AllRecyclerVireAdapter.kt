package com.example.pewpew.view.main.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pewpew.R
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.view.main.AllFragmentViewModel
import com.squareup.picasso.Picasso
private const val TAG = "ADAPTERALL"

class AllRecyclerVireAdapter(val viewModel: AllFragmentViewModel) :
    RecyclerView.Adapter<AllRecyclerVireAdapter.AllViewHolder>() {
    val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<MenuModelItem>(){
        override fun areItemsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuModelItem, newItem: MenuModelItem): Boolean {
            return oldItem == newItem
        }}
        private val differ = AsyncListDiffer(this,DIFF_CALL_BACK)

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

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val item = differ.currentList[position]
        Log.d(TAG,item.name)
        var count = 1
        holder.titleTextView.text = item.name
        holder.priceTextView.text = "${item.price*count} SR"
        holder.addButton.setOnClickListener {
        }
        Picasso.get().load(item.image).into(holder.itemImageView)


        holder.quantity.text = count.toString()
        holder.increaseButton.setOnClickListener {
            count++
            holder.quantity.text = count.toString()
            holder.priceTextView.text = "${item.price*count} SR"
        }
        holder.decreaseButton.setOnClickListener {
            if (count > 1){
                count--
                holder.quantity.text = count.toString()
                holder.priceTextView.text = "${item.price*count} SR"
            }

        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<MenuModelItem>) {
        differ.submitList(list)
    }

    class AllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemimageView)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val increaseButton: Button = itemView.findViewById(R.id.increase)
        val decreaseButton: Button = itemView.findViewById(R.id.decrease)
        val quantity: TextView = itemView.findViewById(R.id.integer_number)
    }
}