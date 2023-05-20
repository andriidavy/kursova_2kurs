package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCreatedCustomItemBinding
import com.example.registration.databinding.ListEmployeeForAssigneToCustomItemBinding
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.users.EmployeeProfileDTO

class EmployeeForAssigneeAdapter (private var employeeDTOList: List<EmployeeProfileDTO>) :
    RecyclerView.Adapter<EmployeeForAssigneeAdapter.ViewHolder>() {

    private lateinit var mListener: EmployeeForAssigneeAdapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(
        var view: ListEmployeeForAssigneToCustomItemBinding,
        listener: EmployeeForAssigneeAdapter.OnItemClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): EmployeeForAssigneeAdapter.ViewHolder {
        val binding =
            ListEmployeeForAssigneToCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return EmployeeForAssigneeAdapter.ViewHolder(binding, mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.employeeId.text = employeeDTOList[position].id.toString()
        viewHolder.view.employeeName.text = employeeDTOList[position].name
        viewHolder.view.employeeSurname.text = employeeDTOList[position].surname
        viewHolder.view.employeeEmail.text = employeeDTOList[position].email
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = employeeDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEmployees(newEmployees: List<EmployeeProfileDTO>) {
        employeeDTOList = newEmployees
        notifyDataSetChanged()
    }
}