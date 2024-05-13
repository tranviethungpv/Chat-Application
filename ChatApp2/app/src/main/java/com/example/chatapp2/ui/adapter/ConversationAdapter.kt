package com.example.chatapp2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp2.R
import com.example.chatapp2.databinding.ItemConversationBinding
import com.example.chatapp2.utils.convertTimestampToTimeDate

class ConversationAdapter(
    private val userId: Int,
    private val onItemClick: (MessageWithReceiverAndConversationInfo) -> Unit,
    private val onLongItemClick: (MessageWithReceiverAndConversationInfo) -> Unit
) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {

    private var dataSet: List<MessageWithReceiverAndConversationInfo> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemConversationBinding = ItemConversationBinding.bind(view)
        val name = binding.tvUsername
        val image = binding.imageMemojiBoysFortyOne
        val latestMessage = binding.tvMessage
        val time = binding.tvTime
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_conversation, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("DiscouragedApi", "SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = dataSet[position].receiver.name
        if (userId == dataSet[position].latestMessage.senderId) {
            viewHolder.latestMessage.text = "You: " + dataSet[position].latestMessage.text
        } else {
            viewHolder.latestMessage.text = dataSet[position].latestMessage.text
        }
        viewHolder.time.text = convertTimestampToTimeDate(dataSet[position].latestMessage.timestamp)

        val resId = viewHolder.image.context.resources.getIdentifier(
            dataSet[position].receiver.avatar, "drawable", viewHolder.image.context.packageName
        )
        Glide.with(viewHolder.image.context).load(resId).into(viewHolder.image)
        viewHolder.itemView.setOnClickListener {
            onItemClick(dataSet[position])
        }
        viewHolder.itemView.setOnLongClickListener {
            onLongItemClick(dataSet[position])
            true
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: List<MessageWithReceiverAndConversationInfo>) {
        this.dataSet = newData
        notifyDataSetChanged()
    }
}