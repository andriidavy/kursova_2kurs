package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListProductItemBinding
import com.example.registration.model.product.Product

open class ProductAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class ViewHolder(var view: ListProductItemBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(view.root) {
        init{
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListProductItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.product = productList[position]
        viewHolder.view.productListItemName.text = productList[position].name
        viewHolder.view.idForProduct.text=productList[position].id.toString()
        viewHolder.view.countForProduct.text =productList[position].quantity.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }

}