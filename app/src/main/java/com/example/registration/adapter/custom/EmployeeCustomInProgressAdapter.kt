package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListInProgressCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class EmployeeCustomInProgressAdapter(
    private var customDTOList: List<CustomDTO>,
    private val onItemClick: (Int) -> Unit,
    private val onCreateReportClick: (Int) -> Unit
) : RecyclerView.Adapter<EmployeeCustomInProgressAdapter.ViewHolder>() {

    class ViewHolder(
        var view: ListInProgressCustomItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ListInProgressCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(
            binding
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForCustom.text = customDTOList[position].customId.toString()
            statusForCustom.text = customDTOList[position].status
            if (statusForCustom.text == "WAITING_RESPONSE") {
                buttonCreateReport.visibility = View.GONE
            }

            root.setOnClickListener {
                onItemClick.invoke(position)
            }

            buttonCreateReport.setOnClickListener {
                onCreateReportClick.invoke(customDTOList[position].customId)
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