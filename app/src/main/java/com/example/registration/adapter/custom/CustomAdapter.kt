package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class CustomAdapter(private var customDTOList: List<CustomDTO>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener: CustomAdapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(var view: ListCustomItemBinding, listener: CustomAdapter.OnItemClickListener) : RecyclerView.ViewHolder(view.root) {
        init{
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val binding =
            ListCustomItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CustomAdapter.ViewHolder(binding, mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.idForCustom.text = customDTOList[position].customId.toString()
        viewHolder.view.statusForCustom.text = customDTOList[position].status
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newProducts: List<CustomDTO>) {
        customDTOList = newProducts
        notifyDataSetChanged()
    }
}