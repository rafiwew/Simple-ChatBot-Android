package com.piwew.mychatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.piwew.mychatbot.databinding.ActivityMainBinding
import com.piwew.mychatbot.retrofit.ApiConfig
import com.piwew.mychatbot.retrofit.ChatResponse
import com.piwew.mychatbot.retrofit.ReqBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var messageList: MutableList<Message> = ArrayList()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageAdapter = MessageAdapter(messageList)
        binding.recyclerView.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply { stackFromEnd = true }
        }

        binding.sendBtn.setOnClickListener {
            val question = binding.messageEditText.text.toString().trim()
            addToChat(question)
            binding.messageEditText.text.clear()
            callAPI(question)
            binding.welcomeText.visibility = View.GONE
        }
    }

    private fun addToChat(message: String) {
        runOnUiThread {
            messageList.add(Message(message, Message.SENT_BY_ME))
            messageAdapter.notifyItemInserted(messageList.size - 1)
            binding.recyclerView.smoothScrollToPosition(messageList.size - 1)
        }
    }

    private fun addResponse(response: String) {
        runOnUiThread {
            if (messageList.isNotEmpty()) {
                messageList.removeAt(messageList.size - 1)
                messageList.add(Message(response, Message.SENT_BY_BOT))
                messageAdapter.notifyItemChanged(messageList.size - 1)
                binding.recyclerView.smoothScrollToPosition(messageList.size - 1)
            }
        }
    }


    private fun callAPI(question: String) {
        messageList.add(Message("Typing... ", Message.SENT_BY_BOT))

        val requestBody = ReqBody(
            model = "gpt-3.5-turbo-instruct",
            prompt = question,
            max_tokens = 5,
            temperature = 0
        )

        val call = ApiConfig.getApiService().getResponse(requestBody)
        call.enqueue(object : Callback<ChatResponse> {

            override fun onResponse(
                call: Call<ChatResponse>,
                response: Response<ChatResponse>,
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.text
                    addResponse(result?.trim() ?: "Empty response")
                } else {
                    Toast.makeText(this@MainActivity, "Not successful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}