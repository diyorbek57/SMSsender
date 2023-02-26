package uz.seppuku.smssender.models

import kotlinx.serialization.Serializable

@Serializable
data class SendData(val phone: String,
                    val body: String)

@Serializable
data class GetData(
    val phone: String,
    val status: String
)