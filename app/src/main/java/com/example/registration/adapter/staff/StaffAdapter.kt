package com.example.registration.adapter.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.StaffItemBinding
import com.example.registration.model.users.StaffDTO

class StaffAdapter(
    private var staffDTOList: List<StaffDTO>
) :
    RecyclerView.Adapter<StaffAdapter.ViewHolder>() {

    class ViewHolder(
        var view: StaffItemBinding
    ) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            StaffItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            staffType.text = staffDTOList[position].position
            staffId.text = staffDTOList[position].id.toString()
            staffName.text = staffDTOList[position].name
            staffSurname.text = staffDTOList[position].surname
            staffEmail.text = staffDTOList[position].email

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = staffDTOList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateStaff(newStaff: List<StaffDTO>) {
        staffDTOList = newStaff
        notifyDataSetChanged()
    }
}