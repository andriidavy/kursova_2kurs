package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class CustomAdapter(
    private var customDTOList: List<CustomDTO>,
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(var view: ListCustomItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListCustomItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForCustom.text = customDTOList[position].customId.toString()
            statusForCustom.text = customDTOList[position].status
            departForCustom.text = customDTOList[position].department
            priceForProduct.text = customDTOList[position].price.toString()

            root.setOnClickListener {
                itemClick.invoke(position)
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