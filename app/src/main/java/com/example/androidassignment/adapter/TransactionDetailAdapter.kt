package com.example.androidassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.data.model.ResultScreenDetail

class TransactionDetailAdapter(private val list: List<ResultScreenDetail>) :
    RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val content = view.findViewById<TextView>(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title

        val content = item.details.joinToString("\n") {
            listOfNotNull(it.value1, it.value2, it.value3).joinToString(" ")
        }

        holder.content.text = content.trim()
    }
}