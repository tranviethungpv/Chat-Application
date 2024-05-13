package com.example.chatapp2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp2.R
import com.example.chatapp2.databinding.ItemReceivedMessageBinding
import com.example.chatapp2.databinding.ItemSentMessageBinding
import com.example.chatapp2.utils.convertTimestampToTimeDate

class MessageAdapter(private val senderId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet: List<MessageWithUsersAndConversation> = listOf()

    class SentMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemSentMessageBinding = ItemSentMessageBinding.bind(view)
        var message = binding.tvMessage
        var time = binding.tvTimestamp
    }

    class ReceivedMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemReceivedMessageBinding = ItemReceivedMessageBinding.bind(view)
        var message = binding.tvMessage
        var time = binding.tvTimestamp
        var image = binding.civUser
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].sender.id == senderId) {
            1
        } else {
            0
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_received_message, viewGroup, false)
            ReceivedMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_sent_message, viewGroup, false)
            SentMessageViewHolder(view)
        }
    }

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is SentMessageViewHolder) {
            viewHolder.message.text = dataSet[position].message.text
            viewHolder.time.text = convertTimestampToTimeDate(dataSet[position].message.timestamp)
        } else if (viewHolder is ReceivedMessageViewHolder) {
            viewHolder.message.text = dataSet[position].message.text
            viewHolder.time.text = convertTimestampToTimeDate(dataSet[position].message.timestamp)
            val resId = viewHolder.image.context.resources.getIdentifier(
                dataSet[position].sender.avatar, "drawable", viewHolder.image.context.packageName
            )
            Glide.with(viewHolder.image.context).load(resId).into(viewHolder.image)
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: List<MessageWithUsersAndConversation>) {
        this.dataSet = newData
        notifyDataSetChanged()
    }
}