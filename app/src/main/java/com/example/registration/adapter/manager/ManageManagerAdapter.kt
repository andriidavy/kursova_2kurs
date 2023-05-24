package com.example.registration.adapter.manager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ManagerListForAdminItemBinding
import com.example.registration.model.users.ManagerProfileDTO

class ManageManagerAdapter(private var managerDTOList: List<ManagerProfileDTO>) :
    RecyclerView.Adapter<ManageManagerAdapter.ViewHolder>() {

    private lateinit var mListener: ManageManagerAdapter.OnItemClickListener
    private lateinit var removeListener: ManageManagerAdapter.OnRemoveManagerClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnRemoveManagerClickListener {
        fun onRemoveManagerClick(position: Int)
    }

    fun setOnRemoveManagerClickListener(listener: ManageManagerAdapter.OnRemoveManagerClickListener) {
        removeListener = listener
    }

    class ViewHolder(
        var view: ManagerListForAdminItemBinding,
        listener: ManageManagerAdapter.OnItemClickListener,
        removeListener: ManageManagerAdapter.OnRemoveManagerClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            view.buttonDeleteManager.setOnClickListener {
                removeListener.onRemoveManagerClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ManageManagerAdapter.ViewHolder {
        val binding =
            ManagerListForAdminItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ManageManagerAdapter.ViewHolder(binding, mListener, removeListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.managerId.text = managerDTOList[position].id.toString()
        viewHolder.view.managerName.text = managerDTOList[position].name
        viewHolder.view.managerSurname.text = managerDTOList[position].surname
        viewHolder.view.managerEmail.text = managerDTOList[position].email
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = managerDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateManagers(newManagers: List<ManagerProfileDTO>) {
        managerDTOList = newManagers
        notifyDataSetChanged()
    }
}