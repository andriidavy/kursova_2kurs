package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ListInProgressCustomItemBinding
import com.example.registration.databinding.ListProcessedCustomItemBinding
import com.example.registration.model.custom.CustomDTO

class EmployeeCustomProcessedAdapter (private var customDTOList: List<CustomDTO>
) :
    RecyclerView.Adapter<EmployeeCustomProcessedAdapter.ViewHolder>() {

    private lateinit var mListener: EmployeeCustomProcessedAdapter.OnItemClickListener
    private lateinit var sendClickListener: EmployeeCustomProcessedAdapter.OnSendClickListener

    interface OnSendClickListener {
        fun onSendClick(customId: Int, position: Int)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun setOnSendClickListener(sendListener: EmployeeCustomProcessedAdapter.OnSendClickListener){
        sendClickListener = sendListener
    }

    class ViewHolder(
        var view: ListProcessedCustomItemBinding,
        listener: EmployeeCustomProcessedAdapter.OnItemClickListener,
        sendListener: EmployeeCustomProcessedAdapter.OnSendClickListener
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            view.buttonSendCustom.setOnClickListener {
                sendListener.onSendClick(Integer.parseInt(view.idForCustom.text.toString()),adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): EmployeeCustomProcessedAdapter.ViewHolder {
        val binding =
            ListProcessedCustomItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return EmployeeCustomProcessedAdapter.ViewHolder(binding, mListener, sendClickListener)
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