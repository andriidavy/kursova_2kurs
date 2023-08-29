package com.example.registration.adapter.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListReportItemBinding
import com.example.registration.model.report.ReportDTO

class ReportAdapter(
    private var reportDTOList: List<ReportDTO>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    class ViewHolder(var view: ListReportItemBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListReportItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForReport.text = reportDTOList[position].reportId.toString()
            customId.text = reportDTOList[position].customId.toString()
            statusForReport.text = reportDTOList[position].status

            root.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = reportDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateReports(newReports: List<ReportDTO>) {
        reportDTOList = newReports
        notifyDataSetChanged()
    }
}