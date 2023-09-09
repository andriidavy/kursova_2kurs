package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListInProgressCustomItemBinding
import com.example.registration.databinding.ListProcessedCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class EmployeeCustomProcessedAdapter(
    private var customDTOList: List<CustomDTO>,
    private val onSendClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EmployeeCustomProcessedAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ListProcessedCustomItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ListProcessedCustomItemBinding.inflate(
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

            buttonSendCustom.setOnClickListener {
                onSendClick.invoke(customDTOList[position].customId)
            }
            root.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newCustoms: List<CustomDTO>) {
        customDTOList = newCustoms
        notifyDataSetChanged()
    }
}