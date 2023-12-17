package com.piwew.mychatbot.retrofit

import com.google.gson.annotations.SerializedName

data class ReqBody(
    @SerializedName("model") val model: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("max_tokens") val max_tokens: Int,
    @SerializedName("temperature") val temperature: Int,
)