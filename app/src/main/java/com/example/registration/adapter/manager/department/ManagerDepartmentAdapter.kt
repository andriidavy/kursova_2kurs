package com.example.registration.adapter.manager.department

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListManagerAssignDepartmentsItemBinding
import com.example.registration.model.department.DepartmentDTO

class ManagerDepartmentAdapter  (private var departmentDTOList: List<DepartmentDTO>) :
    RecyclerView.Adapter<ManagerDepartmentAdapter.ViewHolder>() {

    private lateinit var removeListener: ManagerDepartmentAdapter.OnRemoveManagerClickListener


    interface OnRemoveManagerClickListener {
        fun onRemoveManagerClick(position: Int)
    }

    fun setOnRemoveManagerClickListener(listener: ManagerDepartmentAdapter.OnRemoveManagerClickListener) {
        removeListener = listener
    }

    class ViewHolder(
        var view: ListManagerAssignDepartmentsItemBinding,
        removeListener: ManagerDepartmentAdapter.OnRemoveManagerClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.buttonDeleteAssign.setOnClickListener {
                removeListener.onRemoveManagerClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ManagerDepartmentAdapter.ViewHolder {
        val binding =
            ListManagerAssignDepartmentsItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ManagerDepartmentAdapter.ViewHolder(binding, removeListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.departmentName.text = departmentDTOList[position].departmentName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = departmentDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateDepartments(newDepartments: List<DepartmentDTO>) {
        departmentDTOList = newDepartments
        notifyDataSetChanged()
    }
}