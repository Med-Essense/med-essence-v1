package com.example.test

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView


class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER) {
            UserMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_user, parent, false)
            )
        } else {
            BotMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_bot, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is BotMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size
}

class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val messageText: TextView = view.findViewById(R.id.messageText)
    private val messageImage: ImageView = view.findViewById(R.id.messageImage)

    fun bind(chatMessage: ChatMessage) {
        if (chatMessage.image != null) {
            messageText.visibility = View.GONE
            messageImage.visibility = View.VISIBLE
            messageImage.setImageBitmap(chatMessage.image)
        } else {
            messageText.visibility = View.VISIBLE
            messageImage.visibility = View.GONE
            messageText.text = chatMessage.message
        }
    }
}

class BotMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val messageText: TextView = view.findViewById(R.id.messageText)

    fun bind(chatMessage: ChatMessage) {
        messageText.text = chatMessage.message
    }
}

data class ChatMessage(
    val sender: String,
    val message: String,
    val isUser: Boolean,
    val image: Bitmap? = null
)