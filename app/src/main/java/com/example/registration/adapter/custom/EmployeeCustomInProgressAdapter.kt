package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListInProgressCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class EmployeeCustomInProgressAdapter (
    private var customDTOList: List<CustomDTO>
) :
    RecyclerView.Adapter<EmployeeCustomInProgressAdapter.ViewHolder>() {

    private lateinit var mListener: EmployeeCustomInProgressAdapter.OnItemClickListener
    private lateinit var createReportClickListener: EmployeeCustomInProgressAdapter.OnCreateReportClickListener

    interface OnCreateReportClickListener {
        fun onCreateReportClick(customId: Int, position: Int)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun setOnCreateReportClickListener(reportListener: EmployeeCustomInProgressAdapter.OnCreateReportClickListener){
        createReportClickListener = reportListener
    }

    class ViewHolder(
        var view: ListInProgressCustomItemBinding,
        listener: EmployeeCustomInProgressAdapter.OnItemClickListener,
        reportListener: EmployeeCustomInProgressAdapter.OnCreateReportClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            view.buttonAssignEmployee.setOnClickListener {
                reportListener.onCreateReportClick(Integer.parseInt(view.idForCustom.text.toString()),adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): EmployeeCustomInProgressAdapter.ViewHolder {
        val binding =
            ListInProgressCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return EmployeeCustomInProgressAdapter.ViewHolder(binding, mListener, createReportClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.idForCustom.text = customDTOList[position].customId.toString()
        viewHolder.view.statusForCustom.text = customDTOList[position].status

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCustoms(newCustoms: List<CustomDTO>) {
        customDTOList = newCustoms
        notifyDataSetChanged()
    }
}