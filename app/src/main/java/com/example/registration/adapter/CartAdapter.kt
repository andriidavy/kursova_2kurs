package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCartProductItemBinding
import com.example.registration.model.cart.CartProductDTO

open class CartAdapter (private var cartProductDTOList: List<CartProductDTO>):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(var view: ListCartProductItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListCartProductItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CartAdapter.ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.cartProduct = cartProductDTOList[position]
        viewHolder.view.productListItemName.text = cartProductDTOList[position].productName
        viewHolder.view.idForProduct.text=cartProductDTOList[position].productId.toString()
        viewHolder.view.countForProduct.text =cartProductDTOList[position].quantity.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = cartProductDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newCart: List<CartProductDTO>) {
        cartProductDTOList = newCart
        notifyDataSetChanged()
    }

}