package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.AcceptedFriendItemBinding
import com.example.registration.model.friends.FriendItem

open class AcceptedFriendsAdapter (
    private var friendItemList: List<FriendItem>,
    private val itemRemovedClick: (Int) -> Unit,
    private val itemLocationClick: (Int) -> Unit
) : RecyclerView.Adapter<AcceptedFriendsAdapter.ViewHolder>() {

    class ViewHolder(var view: AcceptedFriendItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AcceptedFriendItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            name.text = friendItemList[position].name
            email.text = friendItemList[position].email
            distance.text = friendItemList[position].distanceToMe.toString()

            btDeleteItem.setOnClickListener {
                itemRemovedClick.invoke(position)
            }

            btSearchItem.setOnClickListener {
                itemLocationClick.invoke(position)
            }

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = friendItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newList: List<FriendItem>) {
        friendItemList = newList
        notifyDataSetChanged()
    }
}