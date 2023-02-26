package uz.seppuku.smssender.di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import uz.seppuku.smssender.repository.MainRepository
import uz.seppuku.smssender.repository.MainRepositoryImpl

val appModule = module {

    single<HttpClient> {
        HttpClient() {
            install(DefaultRequest) {
                header("Accept", "application/json")
                header("Content-type", "application/json")
                contentType(ContentType.Application.Json)
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

single<MainRepository> {
    MainRepositoryImpl()
}

}