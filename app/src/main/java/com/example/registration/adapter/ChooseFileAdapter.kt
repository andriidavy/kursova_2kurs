package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ChooseFileItemBinding
import com.example.registration.model.directoryItem.ServerItem

class ChooseFileAdapter(
    private var serverItemList: List<ServerItem>,
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<ChooseFileAdapter.ViewHolder>() {

    class ViewHolder(var view: ChooseFileItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ChooseFileItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            listItemName.text = serverItemList[position].name
            publicUrl.text = serverItemList[position].publicUrl
            size.text = serverItemList[position].size.toString()
            url.text = serverItemList[position].url
            createdOn.text = serverItemList[position].createdOn.toString()

            root.setOnClickListener {
                itemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = serverItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCart(newList: List<ServerItem>) {
        serverItemList = newList
        notifyDataSetChanged()
    }
}