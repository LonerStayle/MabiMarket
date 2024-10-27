package kr.loner.mabi_market.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FcmApi {
    @POST("projects/mabi-market/messages:send")
    suspend fun sendNotification(
        @Header("Authorization") header:String,
        @Body notification: NotificationRequest
    ): Response<Void>

    data class NotificationRequest(
        val message: Message
    )

    data class Message(
        val token: String, // 수신자의 FCM 토큰
        val notification: NotificationData,
        val data:PayLoadData
    )

    data class NotificationData(
        val title: String,
        val body : String,
    )
    data class PayLoadData(
        val data:Map<String,String>
    )
}