package com.piwew.mychatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piwew.mychatbot.databinding.ItemRowChatBinding

class MessageAdapter(private val messageList: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemRowChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun sentByMe(message: Message) {
            binding.leftChatView.visibility = View.GONE
            binding.rightChatView.visibility = View.VISIBLE
            binding.rightChatTextView.text = message.message
        }

        fun sentByBot(message: Message) {
            binding.rightChatView.visibility = View.GONE
            binding.leftChatView.visibility = View.VISIBLE
            binding.leftChatTextView.text = message.message
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRowChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.sentByMe(message)
        } else {
            holder.sentByBot(message)
        }
    }
}