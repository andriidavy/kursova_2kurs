package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.SearchingFriendItemBinding
import com.example.registration.model.friends.SearchFriendItem

class SearchingFriendsAdapter(
    private var friendItemList: List<SearchFriendItem>,
    private val itemAddClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchingFriendsAdapter.ViewHolder>() {

    class ViewHolder(var view: SearchingFriendItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchingFriendItemBinding.inflate(
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
            btSendInviteItem.setOnClickListener {
                itemAddClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = friendItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newList: List<SearchFriendItem>) {
        friendItemList = newList
        notifyDataSetChanged()
    }
}