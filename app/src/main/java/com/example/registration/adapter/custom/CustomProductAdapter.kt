package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListProductItemBinding
import com.example.registration.model.custom.CustomProductDTO

class CustomProductAdapter(private var customProductDTOList: List<CustomProductDTO>) :
    RecyclerView.Adapter<CustomProductAdapter.ViewHolder>() {

    class ViewHolder(var view: ListProductItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListProductItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForProduct.text = customProductDTOList[position].productId.toString()
            productListItemName.text = customProductDTOList[position].productName
            countForProduct.text = customProductDTOList[position].quantity.toString()
            priceForProduct.text = customProductDTOList[position].price.toString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customProductDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustomProducts(newCustomProductDTOS: List<CustomProductDTO>) {
        customProductDTOList = newCustomProductDTOS
        notifyDataSetChanged()
    }
}