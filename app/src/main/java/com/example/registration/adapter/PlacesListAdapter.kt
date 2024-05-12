package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.LocationItemBinding
import com.example.registration.model.places.PlaceItem

open class PlacesListAdapter(
    private var serverItemList: List<PlaceItem>,
    private val itemRemovedClick: (Int) -> Unit,
    private val itemClick: (Int) -> Unit,
) : RecyclerView.Adapter<PlacesListAdapter.ViewHolder>() {

    class ViewHolder(var view: LocationItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LocationItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {


            buttonDeleteItem.setOnClickListener {
                itemRemovedClick.invoke(position)
            }

            root.setOnClickListener {
                itemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = serverItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newList: List<PlaceItem>) {
        serverItemList = newList
        notifyDataSetChanged()
    }
}