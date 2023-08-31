package com.example.registration.adapter.manager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ManagerListForAdminItemBinding
import com.example.registration.model.users.ManagerProfileDTO

class ManageManagerAdapter(
    private var managerDTOList: List<ManagerProfileDTO>,
    private val onItemClick: (Int) -> Unit,
    private val onItemRemoveClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ManageManagerAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ManagerListForAdminItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ManagerListForAdminItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            managerId.text = managerDTOList[position].id.toString()
            managerName.text = managerDTOList[position].name
            managerSurname.text = managerDTOList[position].surname
            managerEmail.text = managerDTOList[position].email

            root.setOnClickListener {
                onItemClick.invoke(position)
            }

            buttonDeleteManager.setOnClickListener {
                onItemRemoveClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = managerDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateManagers(newManagers: List<ManagerProfileDTO>) {
        managerDTOList = newManagers
        notifyDataSetChanged()
    }
}