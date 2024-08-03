package kr.loner.mabi_market.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RemoteModule {
    //open Api 확인 필요

    const val BASE_URL = "https://mabinogi.co.kr/"
    private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }


    private fun getHeaderInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .build()
        chain.proceed(newRequest)
    }

    fun getMabinogiApi(): MabinogiApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(getHeaderInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()

    }

}