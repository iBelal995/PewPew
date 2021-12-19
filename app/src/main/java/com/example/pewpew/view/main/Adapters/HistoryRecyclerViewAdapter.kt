package com.example.pewpew.view.main.Adaptersimport

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pewpew.R
import com.example.pewpew.model.HistoryModel
import com.example.pewpew.view.main.Adapters.TAG
import com.example.pewpew.view.main.CartFragment
import com.example.pewpew.view.main.OrderHistoryViewModel
import java.text.SimpleDateFormat

const val TAG1 = "ADAPTERhistory"
private var historyList = listOf<HistoryModel>()
class HistoryRecyclerViewAdapter(val viewmodel:OrderHistoryViewModel) :
    RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>() {
    val bundle = Bundle()
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
    ): HistoryRecyclerViewAdapter.HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout_history,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = differ.currentList[position]
        Log.d(TAG1, item.name)
        holder.orderNumber.text = "Order number: ${item.ordernumber}"
        holder.date.text = SimpleDateFormat("MM/dd/yy hh:mm aaa").format(item.date).toString()
        holder.priceTextView.text = "Total price:(including Tax) ${item.totalprice} SR"
        holder.itemView.setOnClickListener {
            bundle.putInt("ordernumber",item.ordernumber)
            it.findNavController().navigate(R.id.action_orderHistoryFragment_to_specificOrderFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<HistoryModel>) {

        differ.submitList(list.distinctBy { it.ordernumber })
    }
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderNumber: TextView = itemView.findViewById(R.id.ordernumber)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val date: TextView = itemView.findViewById(R.id.dateTextView)
    }
}