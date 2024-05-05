package com.example.registration.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ServerFileItemBinding
import com.example.registration.model.directoryItem.ServerItem

open class ServerFilesListAdapter(
    private var serverItemList: List<ServerItem>,
    private val itemRemovedClick: (Int) -> Unit,
    private val itemClick: (Int) -> Unit,
    private val itemDownloadClick: (Int) -> Unit
) : RecyclerView.Adapter<ServerFilesListAdapter.ViewHolder>() {

    class ViewHolder(var view: ServerFileItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ServerFileItemBinding.inflate(
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

            if (serverItemList[position].size.toString() == "0") {
                publicUrl.visibility = View.GONE
                publicUrlText.visibility = View.GONE
                size.visibility = View.GONE
                sizeText.visibility = View.GONE
                buttonDownload.isEnabled = false
                buttonDownload.visibility = View.INVISIBLE
            } else {
                root.isEnabled = false
            }

            buttonDeleteItem.setOnClickListener {
                itemRemovedClick.invoke(position)
            }

            buttonDownload.setOnClickListener {
                itemDownloadClick.invoke(position)
            }

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