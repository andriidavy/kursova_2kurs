package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.PlaceItemBinding
import com.example.registration.model.places.PlaceItem

open class PlacesListAdapter(
    private var placesItemList: List<PlaceItem>,
    private val itemRemovedClick: (Int) -> Unit,
    private val itemClick: (Int) -> Unit,
) : RecyclerView.Adapter<PlacesListAdapter.ViewHolder>() {

    class ViewHolder(var view: PlaceItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PlaceItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            description.text = placesItemList[position].description
            tags.text = placesItemList[position].tags
            imageUrl.text = placesItemList[position].placePhotoUrl
            createdOn.text = placesItemList[position].created.toString()

            buttonDeleteItem.setOnClickListener {
                itemRemovedClick.invoke(position)
            }

            root.setOnClickListener {
                itemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = placesItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newList: List<PlaceItem>) {
        placesItemList = newList
        notifyDataSetChanged()
    }
}