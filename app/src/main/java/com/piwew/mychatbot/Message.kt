package com.piwew.mychatbot

class Message(
    val message: String,
    val sentBy: String,
) {
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}