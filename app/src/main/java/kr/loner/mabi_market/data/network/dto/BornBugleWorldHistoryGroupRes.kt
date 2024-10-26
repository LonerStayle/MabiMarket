package kr.loner.mabi_market.data.network.dto

import com.google.gson.annotations.SerializedName

data class BornBugleWorldHistoryGroupRes(
    @SerializedName("horn_bugle_world_history")
    val hornBugleWorldHistory: List<BornBugleWorldHistoryRes>
)
