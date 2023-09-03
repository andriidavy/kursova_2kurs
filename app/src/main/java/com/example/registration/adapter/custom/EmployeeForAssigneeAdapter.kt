package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListEmployeeForAssigneToCustomItemBinding
import com.example.registration.model.users.EmployeeProfileDTO

class EmployeeForAssigneeAdapter(
    private var employeeDTOList: List<EmployeeProfileDTO>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EmployeeForAssigneeAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ListEmployeeForAssigneToCustomItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ListEmployeeForAssigneToCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            employeeId.text = employeeDTOList[position].id.toString()
            employeeName.text = employeeDTOList[position].name
            employeeSurname.text = employeeDTOList[position].surname
            employeeEmail.text = employeeDTOList[position].email

            root.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = employeeDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEmployees(newEmployees: List<EmployeeProfileDTO>) {
        employeeDTOList = newEmployees
        notifyDataSetChanged()
    }
}