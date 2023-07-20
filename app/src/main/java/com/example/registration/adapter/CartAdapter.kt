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
    private val customerCartPageViewModel: CustomerCartPageViewModel
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ListCartProductItemBinding,
        val viewModel: CustomerCartPageViewModel
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListCartProductItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return CartAdapter.ViewHolder(binding, customerCartPageViewModel)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.productListItemName.text = cartProductDTOList[position].productName
        viewHolder.view.idForProduct.text = cartProductDTOList[position].productId.toString()
        viewHolder.view.countForProduct.text = cartProductDTOList[position].quantity.toString()

        val productId: Int = Integer.parseInt(cartProductDTOList[position].productId.toString())
        viewHolder.view.buttonDeleteItem.setOnClickListener {
            viewHolder.viewModel.removeProductFromCart(productId)
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