package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListEmployeeForAssigneToCustomItemBinding
import com.example.registration.databinding.ListManageEmployeeItemBinding
import com.example.registration.model.users.EmployeeProfileDTO

class ManageEmployeeAdapter (private var employeeDTOList: List<EmployeeProfileDTO>) :
    RecyclerView.Adapter<ManageEmployeeAdapter.ViewHolder>() {

    private lateinit var removeListener: ManageEmployeeAdapter.OnRemoveEmployeeClickListener


    interface OnRemoveEmployeeClickListener {
        fun onRemoveEmployeeClick(position: Int)
    }

    fun setOnRemoveEmployeeClickListener(listener: OnRemoveEmployeeClickListener) {
        removeListener = listener
    }

    class ViewHolder(
        var view: ListManageEmployeeItemBinding,
        listener: ManageEmployeeAdapter.OnRemoveEmployeeClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.buttonDeleteEmployee.setOnClickListener {
                listener.onRemoveEmployeeClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ManageEmployeeAdapter.ViewHolder {
        val binding =
            ListManageEmployeeItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ManageEmployeeAdapter.ViewHolder(binding, removeListener)
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