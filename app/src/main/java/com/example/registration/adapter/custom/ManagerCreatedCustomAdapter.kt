package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCreatedCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class ManagerCreatedCustomAdapter(
    private var customDTOList: List<CustomDTO>,
    private val onAssignEmployeeClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ManagerCreatedCustomAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ListCreatedCustomItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListCreatedCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val customId = customDTOList[position].customId

        viewHolder.view.apply {
            idForCustom.text = customId.toString()
            statusForCustom.text = customDTOList[position].status
            departForCustom.text = customDTOList[position].department

            buttonAssignEmployee.setOnClickListener {
                onAssignEmployeeClick.invoke(customId)
            }

            root.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newProducts: List<CustomDTO>) {
        customDTOList = newProducts
        notifyDataSetChanged()
    }
}