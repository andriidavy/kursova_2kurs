package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ItemMessageReceiverBinding
import com.example.registration.databinding.ItemMessageSenderBinding
import com.example.registration.model.message.MessageDTO

class MessagingAdapter (
    private var messageDTOList: List<MessageDTO>,
    private val userRole: String // "CUSTOMER" или "MANAGER"
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SENDER = 1
    private val VIEW_TYPE_RECEIVER = 2

    override fun getItemViewType(position: Int): Int {
        return if (messageDTOList[position].role == userRole) VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val binding = ItemMessageSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SenderViewHolder(binding)
        } else {
            val binding = ItemMessageReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiverViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageDTOList[position]
        if (holder is SenderViewHolder) {
            holder.binding.messageText.text = message.text
        } else if (holder is ReceiverViewHolder) {
            holder.binding.messageText.text = message.text
        }
    }

    override fun getItemCount() = messageDTOList.size

    class SenderViewHolder(val binding: ItemMessageSenderBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ItemMessageReceiverBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<MessageDTO>) {
        messageDTOList = newMessages
        notifyDataSetChanged()
    }
}