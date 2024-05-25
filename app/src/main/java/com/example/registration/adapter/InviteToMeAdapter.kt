package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.InviteToMeItemBinding
import com.example.registration.model.friends.FriendItem
import com.example.registration.model.friends.SearchFriendItem

open class InviteToMeAdapter(
    private var friendItemList: List<FriendItem>,
    private val itemAcceptClick: (Int) -> Unit,
    private val itemRejectClick: (Int) -> Unit
) : RecyclerView.Adapter<InviteToMeAdapter.ViewHolder>() {

    class ViewHolder(var view: InviteToMeItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            InviteToMeItemBinding.inflate(
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
            btAcceptInviteItem.setOnClickListener {
                itemAcceptClick.invoke(position)
            }
            btRejectInviteItem.setOnClickListener {
                itemRejectClick.invoke(position)
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