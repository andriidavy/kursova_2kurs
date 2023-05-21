package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListAllCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class ManagerAllCustomAdapter(private var customDTOList: List<CustomDTO>) :
    RecyclerView.Adapter<ManagerAllCustomAdapter.ViewHolder>() {

    private lateinit var mListener: ManagerAllCustomAdapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(var view: ListAllCustomItemBinding, listener: ManagerAllCustomAdapter.OnItemClickListener) : RecyclerView.ViewHolder(view.root) {
        init{
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ManagerAllCustomAdapter.ViewHolder {
        val binding =
            ListAllCustomItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ManagerAllCustomAdapter.ViewHolder(binding, mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.idForCustom.text = customDTOList[position].customId.toString()
        viewHolder.view.statusForCustom.text = customDTOList[position].status
        viewHolder.view.customerId.text = customDTOList[position].customerId.toString()
        viewHolder.view.customerName.text = customDTOList[position].customerName
        viewHolder.view.customerSurname.text = customDTOList[position].customerSurname
        if(viewHolder.view.statusForCustom.text != "CREATED") {
            viewHolder.view.employeeText.visibility = View.VISIBLE
            viewHolder.view.employeeId.visibility = View.VISIBLE
            viewHolder.view.employeeNameText.visibility = View.VISIBLE
            viewHolder.view.employeeName.visibility = View.VISIBLE
            viewHolder.view.employeeSurnameText.visibility = View.VISIBLE
            viewHolder.view.employeeSurname.visibility = View.VISIBLE
            viewHolder.view.employeeId.text = customDTOList[position].employeeId.toString()
            viewHolder.view.employeeName.text = customDTOList[position].employeeName
            viewHolder.view.employeeSurname.text = customDTOList[position].employeeSurname
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newProducts: List<CustomDTO>) {
        customDTOList = newProducts
        notifyDataSetChanged()
    }
}