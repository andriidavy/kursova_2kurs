package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCartProductItemBinding
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.ui.customer.cart.CustomerCartPageViewModel

open class CartAdapter(
    private var cartProductDTOList: List<CartProductDTO>,
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
private lateinit var removeProductClickListener: OnRemoveProductClickListener
    interface OnRemoveProductClickListener{
        fun onRemoveProductClick(position: Int)
    }

    fun setOnRemoveProductClickListener(removeListener: OnRemoveProductClickListener){
        removeProductClickListener = removeListener
    }


    class ViewHolder(
        var view: ListCartProductItemBinding,
        var removeListener: OnRemoveProductClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.buttonDeleteItem.setOnClickListener {
                removeListener.onRemoveProductClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListCartProductItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding, removeProductClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            productListItemName.text = cartProductDTOList[position].productName
            idForProduct.text = cartProductDTOList[position].productId.toString()
            countForProduct.text = cartProductDTOList[position].quantity.toString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = cartProductDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newCart: List<CartProductDTO>) {
        cartProductDTOList = newCart
        notifyDataSetChanged()
    }
}