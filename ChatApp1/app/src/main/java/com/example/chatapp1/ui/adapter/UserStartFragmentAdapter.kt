package com.example.chatapp1.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp1.R
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.chatapp1.User
import com.example.chatapp1.databinding.ItemUserStartFragmentBinding

class UserStartFragmentAdapter(private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserStartFragmentAdapter.ViewHolder>() {

    private var dataSet: List<User> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemUserStartFragmentBinding = ItemUserStartFragmentBinding.bind(view)
        val name = binding.tvName
        val image = binding.civAvatar
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user_start_fragment, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = dataSet[position].name

        val resId = viewHolder.image.context.resources.getIdentifier(
            dataSet[position].avatar, "drawable", viewHolder.image.context.packageName
        )
        Glide.with(viewHolder.image.context).load(resId).into(viewHolder.image)
        viewHolder.itemView.setOnClickListener {
            onItemClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newData: List<User>) {
        this.dataSet = newData
        notifyDataSetChanged()
    }
}