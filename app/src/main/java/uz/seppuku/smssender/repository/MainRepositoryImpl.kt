package uz.seppuku.smssender.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import uz.seppuku.smssender.utils.NetworkResponse
import uz.seppuku.mcarv1.utils.NetworkResponseCode.Companion.checkError
import uz.seppuku.smssender.models.GetData
import uz.seppuku.smssender.models.SendData

class MainRepositoryImpl : MainRepository,KoinComponent {

    private val client: HttpClient by inject()

    override suspend fun send(sendData: SendData): NetworkResponse<String> {
        return try {
            val response = client.submitForm(
                url = HttpRoutes.POST,
                formParameters = Parameters.build {
                    append("phone", sendData.phone)
                    append("body", sendData.body)

                })
            NetworkResponse.Success(response.body())
        } catch (e: Throwable) {
            (NetworkResponse.Error(checkError(e)))
        }
    }

    override suspend fun get(): NetworkResponse<GetData> {
        return try {
            val response =  client.get(HttpRoutes.GET)
            (NetworkResponse.Success(response.body()))
        } catch (e: Throwable) {
            (NetworkResponse.Error(checkError(e)))
        }
    }
}