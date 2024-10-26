package kr.loner.mabi_market.data.model

import com.google.gson.annotations.SerializedName

data class AuctionList(
    @SerializedName("auction_item")
    val auctionItems:List<NewItem>
)
