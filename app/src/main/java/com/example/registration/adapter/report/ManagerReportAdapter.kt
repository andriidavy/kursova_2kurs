package com.example.registration.adapter.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListReportItemBinding
import com.example.registration.model.report.ReportDTO

class ManagerReportAdapter(
    private var reportDTOList: List<ReportDTO>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ManagerReportAdapter.ViewHolder>() {

    private var originalIndexes: List<Int> = emptyList()

    class ViewHolder(var view: ListReportItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListReportItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            idForReport.text = reportDTOList[position].reportId.toString()
            customId.text = reportDTOList[position].customId.toString()
            statusForReport.text = reportDTOList[position].status

            root.setOnClickListener {
                onItemClick.invoke(originalIndexes[position])
            }
        }
    }

    override fun getItemCount() = reportDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateReports(newReports: List<ReportDTO>, newIndexes: List<Int>) {
        reportDTOList = newReports
        originalIndexes = newIndexes
        notifyDataSetChanged()
    }
}