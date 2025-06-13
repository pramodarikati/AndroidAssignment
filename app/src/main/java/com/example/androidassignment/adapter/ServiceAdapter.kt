package com.example.androidassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.data.model.Service
import com.example.androidassignment.data.model.SubService
import com.example.androidassignment.databinding.ItemServiceBinding

class ServiceAdapter(
    private val services: List<Service>,
    private val onSubServiceClick: (SubService) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.binding.tvServiceName.text = service.name

        holder.binding.tvServiceName.setOnClickListener {
            service.isExpanded = !service.isExpanded
            notifyItemChanged(position)
        }

        holder.binding.layoutSubServices.removeAllViews()

        if (service.isExpanded) {
            service.subServices.forEach { sub ->
                val cb = CheckBox(holder.itemView.context)
                cb.text = sub.name
                cb.isChecked = sub.isSelected
                cb.setOnClickListener {
                    onSubServiceClick(sub)
                    cb.isChecked = sub.isSelected
                }
                holder.binding.layoutSubServices.addView(cb)
            }
        }
    }

    override fun getItemCount(): Int = services.size
}