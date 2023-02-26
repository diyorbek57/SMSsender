package uz.seppuku.smssender.repository


import uz.seppuku.smssender.BuildConfig

object HttpRoutes{
    private const val BASE_URL = BuildConfig.BASE_URL
    const val POST = "$BASE_URL/post"
    const val GET = "$BASE_URL/get"

}