package kr.loner.mabi_market.data.network


import com.google.gson.GsonBuilder
import kr.loner.mabi_market.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

object RemoteModule {
    //open Api 확인 필요

    const val BASE_URL = "https://open.api.nexon.com/mabinogi/"
    private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }


    private fun getHeaderInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("x-nxopen-api-key", BuildConfig.API_KEY)
            .build()
        chain.proceed(newRequest)
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
        .create()

    fun getMabinogiApi(): MabinogiApi {
        val client = OkHttpClient.Builder()

            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(getHeaderInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create()

    }


    fun getFcmApi(): FcmApi {
        val client = OkHttpClient.Builder()

            .addInterceptor(getHttpLoggingInterceptor())
            .build()
        return Retrofit.Builder()
//            .baseUrl("https://fcm.googleapis.com/v1/")
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create()

    }

}