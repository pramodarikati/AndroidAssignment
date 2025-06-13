package com.example.androidassignment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.TransactionSuccessActivity
import com.example.androidassignment.data.model.SubService
import com.example.androidassignment.databinding.ItemQuickAccessBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SelectedSubServiceAdapter(private val list: List<SubService>) :
    RecyclerView.Adapter<SelectedSubServiceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemQuickAccessBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuickAccessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvLabel.text = item.name
        holder.binding.ivIcon.setImageResource(item.iconResId)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TransactionSuccessActivity::class.java).apply {
                putExtra("header", "Transaction Successful!")
                val dateTime = SimpleDateFormat("hh:mm a, 'on' dd MMM yyyy", Locale.getDefault()).format(
                    Date()
                )
                putExtra("date", dateTime)
                putExtra("recipientName", item.name)
                putExtra("amount", "GHS 5.00")
            }
            context.startActivity(intent)
        }
    }

}