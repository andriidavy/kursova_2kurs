package com.example.registration.adapter.manager.department

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.databinding.ListAllDepartmentsBinding
import com.example.registration.model.department.DepartmentDTO

class AllDepartmentAdapter(
    private var departmentDTOList: List<DepartmentDTO>,
    private val itemClicked: (Int) -> Unit
) : RecyclerView.Adapter<AllDepartmentAdapter.ViewHolder>() {

    class ViewHolder(var view: ListAllDepartmentsBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ListAllDepartmentsBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            departmentName.text = departmentDTOList[position].departmentName

            root.setOnClickListener {
                itemClicked.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = departmentDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateDepartments(newDepartments: List<DepartmentDTO>) {
        departmentDTOList = newDepartments
        notifyDataSetChanged()
    }
}