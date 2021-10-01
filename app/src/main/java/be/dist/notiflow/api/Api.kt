package be.dist.notiflow.api

import be.dist.notiflow.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

const val debugBaseUrl = "https://api.notiflow.dist.be"
var botBaseUrl = ""

val interceptor = HttpLoggingInterceptor()
    .apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

val client = OkHttpClient()
    .newBuilder()
    .addNetworkInterceptor(interceptor)
    .build()

val debugApi: Api = Retrofit.Builder()
    .baseUrl(debugBaseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(Api::class.java)

val botApi: Api
    get() = Retrofit.Builder()
        .baseUrl(botBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(Api::class.java)

interface Api {
    @POST("-/{room}")
    suspend fun sendDebug(
        @Path(value = "room", encoded = true) path: String,
        @Body requestBody: RequestBody
    ): Response

    @POST("{path}")
    suspend fun sendBot(
        @Path(value = "path", encoded = true) path: String,
        @Body requestBody: RequestBody
    ): Response
}
