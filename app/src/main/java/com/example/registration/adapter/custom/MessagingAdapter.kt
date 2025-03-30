package com.example.registration.adapter.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registration.databinding.ItemMessageReceiverBinding
import com.example.registration.databinding.ItemMessageSenderBinding
import com.example.registration.model.message.MessageDTO

class MessagingAdapter(
    private var messageDTOList: MutableList<MessageDTO>,
    private val userRole: String, // "CUSTOMER" или "MANAGER"
    private val recyclerView: RecyclerView // Ссылка на RecyclerView для прокрутки
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
        val formattedTime = message.creationMessageTime.replace("T", " ").substring(0, 16)
        if (holder is SenderViewHolder) {
            holder.binding.messageText.text = message.text
            holder.binding.messageTime.text = formattedTime
        } else if (holder is ReceiverViewHolder) {
            holder.binding.messageText.text = message.text
            holder.binding.messageTime.text = formattedTime
        }
    }

    override fun getItemCount() = messageDTOList.size

    class SenderViewHolder(val binding: ItemMessageSenderBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ItemMessageReceiverBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateMessages(newMessages: List<MessageDTO>) {
        val oldSize = messageDTOList.size
        messageDTOList.clear()
        messageDTOList.addAll(newMessages)
        notifyItemRangeInserted(oldSize, newMessages.size)

        scrollToLastMessage()
    }

    private fun scrollToLastMessage() {
        recyclerView.post {
            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition() ?: 0
            val totalItemCount = layoutManager?.itemCount ?: 0

            // Прокручиваем вниз только если пользователь уже был внизу
            if (lastVisibleItemPosition >= totalItemCount - 2) {
                recyclerView.scrollToPosition(messageDTOList.size - 1)
            }
        }
    }
}