package uz.seppuku.smssender.repository

import uz.seppuku.smssender.utils.NetworkResponse
import uz.seppuku.smssender.models.GetData
import uz.seppuku.smssender.models.SendData


interface MainRepository {

    suspend fun send(sendData: SendData): NetworkResponse<String>
    suspend fun get(): NetworkResponse<GetData>
}