package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListAllCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class ManagerAllCustomAdapter(
    private var customDTOList: List<CustomDTO>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ManagerAllCustomAdapter.ViewHolder>() {

    class ViewHolder(var view: ListAllCustomItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ListAllCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForCustom.text = customDTOList[position].customId.toString()
            statusForCustom.text = customDTOList[position].status
            departForCustom.text = customDTOList[position].department
            customerId.text = customDTOList[position].customerId.toString()
            customerName.text = customDTOList[position].customerName
            customerSurname.text = customDTOList[position].customerSurname
            if (statusForCustom.text != "CREATED") {
                employeeId.text = customDTOList[position].employeeId.toString()
                employeeName.text = customDTOList[position].employeeName
                employeeSurname.text = customDTOList[position].employeeSurname
                employeeText.visibility = View.VISIBLE
                employeeId.visibility = View.VISIBLE
                employeeNameText.visibility = View.VISIBLE
                employeeName.visibility = View.VISIBLE
                employeeSurnameText.visibility = View.VISIBLE
                employeeSurname.visibility = View.VISIBLE
            } else {
                employeeText.visibility = View.GONE
                employeeId.visibility = View.GONE
                employeeNameText.visibility = View.GONE
                employeeName.visibility = View.GONE
                employeeSurnameText.visibility = View.GONE
                employeeSurname.visibility = View.GONE
            }

            root.setOnClickListener {
                onItemClick.invoke(position)
            }
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