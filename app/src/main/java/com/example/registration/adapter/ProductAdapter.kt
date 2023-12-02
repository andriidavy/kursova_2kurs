package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListProductItemBinding
import com.example.registration.model.product.ProductDTO

open class ProductAdapter(
    private var productDTOList: List<ProductDTO>,
    private var itemClicked: (Int) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

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
            productListItemName.text = productDTOList[position].name
            idForProduct.text = productDTOList[position].id.toString()
            countForProduct.text = productDTOList[position].quantity.toString()
            priceForProduct.text = productDTOList[position].price.toString()

            root.setOnClickListener {
                itemClicked.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProductDTOS: List<ProductDTO>) {
        productDTOList = newProductDTOS
        notifyDataSetChanged()
    }

}