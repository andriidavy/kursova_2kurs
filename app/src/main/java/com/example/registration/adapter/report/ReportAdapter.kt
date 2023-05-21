package com.example.registration.adapter.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.databinding.ListCustomItemBinding
import com.example.registration.databinding.ListReportItemBinding
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO

class ReportAdapter (private var reportDTOList: List<ReportDTO>) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private lateinit var mListener: ReportAdapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(var view: ListReportItemBinding, listener: ReportAdapter.OnItemClickListener) : RecyclerView.ViewHolder(view.root) {
        init{
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReportAdapter.ViewHolder {
        val binding =
            ListReportItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ReportAdapter.ViewHolder(binding, mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.idForReport.text = reportDTOList[position].reportId.toString()
        viewHolder.view.customId.text = reportDTOList[position].customId.toString()
        viewHolder.view.statusForReport.text = reportDTOList[position].status
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = reportDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateReports(newReports: List<ReportDTO>) {
        reportDTOList = newReports
        notifyDataSetChanged()
    }
}