package kr.loner.mabi_market.data.model

import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

data class BornBugleWorldChat(
    @SerializedName("character_name")
    val characterName:String,

    @SerializedName("message")
    val message:String,

    @SerializedName("date_send")
    val dateSend:ZonedDateTime,
)
