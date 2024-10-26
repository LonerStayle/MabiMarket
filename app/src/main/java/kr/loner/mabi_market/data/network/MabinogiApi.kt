package kr.loner.mabi_market.data.network

import kr.loner.mabi_market.data.network.dto.BornBugleWorldHistoryGroupRes
import retrofit2.http.GET
import retrofit2.http.Query


interface MabinogiApi {
    @GET("v1/horn-bugle-world/history")
    suspend fun getBornBugleWorldHistories(
        @Query("server_name") serverName: String
    ): BornBugleWorldHistoryGroupRes
}