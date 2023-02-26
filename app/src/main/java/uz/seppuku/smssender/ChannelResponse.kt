package uz.seppuku.smssender

data class ChannelResponse(
    val channel: String,
    val `data`: String,
    val event: String
)