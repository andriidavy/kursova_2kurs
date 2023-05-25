package com.example.registration.adapter.manager.department

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.databinding.ListAllDepartmentsBinding
import com.example.registration.model.department.DepartmentDTO

class AllDepartmentAdapter(private var departmentDTOList: List<DepartmentDTO>) :
    RecyclerView.Adapter<AllDepartmentAdapter.ViewHolder>() {

    private lateinit var mListener: AllDepartmentAdapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(
        var view: ListAllDepartmentsBinding,
        listener: AllDepartmentAdapter.OnItemClickListener,
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
    ): AllDepartmentAdapter.ViewHolder {
        val binding =
            ListAllDepartmentsBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return AllDepartmentAdapter.ViewHolder(binding, mListener)
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