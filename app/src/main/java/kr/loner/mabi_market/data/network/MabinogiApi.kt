package kr.loner.mabi_market.data.network

import kr.loner.mabi_market.data.model.AuctionList
import kr.loner.mabi_market.data.model.BornBugleWorldChatGroup
import retrofit2.http.GET
import retrofit2.http.Query


interface MabinogiApi {
    @GET("v1/horn-bugle-world/history")
    suspend fun getBornBugleWorldHistories(
        @Query("server_name") serverName: String
    ): BornBugleWorldChatGroup

    @GET("v1/auction/list")
    suspend fun getAuctionList(
        @Query("item_name") itemName: String
    ): AuctionList
}