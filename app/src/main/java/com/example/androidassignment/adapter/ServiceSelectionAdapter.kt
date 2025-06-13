package com.example.androidassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.data.model.Service
import com.example.androidassignment.data.model.SubService

class ServiceSelectionAdapter(
    private val services: List<Service>,
    private val onSubServiceClick: (SubService) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    companion object {
        const val TYPE_SERVICE = 0
        const val TYPE_SUB_SERVICE = 1
    }

    init {
        updateItems()
    }

    private fun updateItems() {
        items.clear()
        services.forEach {
            items.add(it)
            if (it.isExpanded) items.addAll(it.subServices)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Service -> TYPE_SERVICE
            is SubService -> TYPE_SUB_SERVICE
            else -> throw IllegalArgumentException("Invalid type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SERVICE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_service_header, parent, false)
            ServiceViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sub_service, parent, false)
            SubServiceViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ServiceViewHolder && item is Service) {
            holder.bind(item)
        } else if (holder is SubServiceViewHolder && item is SubService) {
            holder.bind(item)
        }
    }

    inner class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tvServiceName)
        private val icon: ImageView = view.findViewById(R.id.ivServiceIcon)

        fun bind(service: Service) {
            title.text = service.name
            icon.setImageResource(service.iconResId)

            itemView.setOnClickListener {
                service.isExpanded = !service.isExpanded
                updateItems()
                notifyDataSetChanged()
            }
        }
    }

    inner class SubServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val checkbox: CheckBox = view.findViewById(R.id.cbSubService)
        private val label: TextView = view.findViewById(R.id.tvSubServiceName)

        fun bind(sub: SubService) {
            label.text = sub.name
            checkbox.isChecked = sub.isSelected
            checkbox.setOnClickListener {
                onSubServiceClick(sub)
                checkbox.isChecked = sub.isSelected
            }
        }
    }

}