package com.piwew.mychatbot.retrofit

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @field:SerializedName("text") val text: String,
)