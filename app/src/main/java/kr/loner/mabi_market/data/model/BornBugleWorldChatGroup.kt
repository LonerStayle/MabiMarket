package kr.loner.mabi_market.data.model

import com.google.gson.annotations.SerializedName
import kr.loner.mabi_market.data.model.BornBugleWorldChat

data class BornBugleWorldChatGroup(
    @SerializedName("horn_bugle_world_history")
    val hornBugleWorldHistory: List<BornBugleWorldChat>
)
