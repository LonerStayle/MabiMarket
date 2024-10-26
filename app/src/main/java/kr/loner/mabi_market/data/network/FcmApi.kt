package kr.loner.mabi_market.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApi {
//    @POST("projects/mabi-market/messages:send")
//    suspend fun sendNotification(
////        @Header("Authorization") auth: String = "Bearer AIzaSyB0wTGZH9rav9YXqHS_Etkp_0-1KX3_X24",
//        @Body notification: NotificationRequest
//    ): Response<Void>

    @Headers(
        "Authorization: key=AIzaSyAfjNSVCAxAPw3v559kGvgB4mdkIFFptA8",
        "Content-Type: application/json"
    )
    @POST("fcm/send")
    suspend fun sendNotification(
        @Body notification: Message
    ): Response<ResponseBody>

    data class NotificationRequest(
        val message: Message
    )

    data class Message(
        val to: String, // 수신자의 FCM 토큰
        val data: NotificationData
    )

    data class NotificationData(
        val title: String,
        val content : String
    )
}