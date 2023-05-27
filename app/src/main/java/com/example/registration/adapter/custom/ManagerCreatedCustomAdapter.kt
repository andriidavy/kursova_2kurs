package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListCreatedCustomItemBinding
import com.example.registration.fragment.managerFragments.createdCustoms.ManagerCreatedCustomsPageFragment
import com.example.registration.model.custom.CustomDTO

class ManagerCreatedCustomAdapter(
    private var customDTOList: List<CustomDTO>
) :
    RecyclerView.Adapter<ManagerCreatedCustomAdapter.ViewHolder>() {

    private lateinit var mListener: ManagerCreatedCustomAdapter.OnItemClickListener
    private lateinit var assignEmployeeClickListener: ManagerCreatedCustomAdapter.OnAssignEmployeeClickListener

    interface OnAssignEmployeeClickListener {
        fun onAssignEmployeeClick(customId: Int, position: Int)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun setOnAssignEmployeeClickListener(assignListener: OnAssignEmployeeClickListener){
     assignEmployeeClickListener = assignListener
    }

    class ViewHolder(
        var view: ListCreatedCustomItemBinding,
        listener: ManagerCreatedCustomAdapter.OnItemClickListener,
        assignListener: ManagerCreatedCustomAdapter.OnAssignEmployeeClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            view.buttonAssignEmployee.setOnClickListener {
                assignListener.onAssignEmployeeClick(Integer.parseInt(view.idForCustom.text.toString()),adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ManagerCreatedCustomAdapter.ViewHolder {
        val binding =
            ListCreatedCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ManagerCreatedCustomAdapter.ViewHolder(binding, mListener, assignEmployeeClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.idForCustom.text = customDTOList[position].customId.toString()
        viewHolder.view.statusForCustom.text = customDTOList[position].status
        viewHolder.view.departForCustom.text = customDTOList[position].department
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newProducts: List<CustomDTO>) {
        customDTOList = newProducts
        notifyDataSetChanged()
    }
}