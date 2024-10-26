package kr.loner.mabi_market.data.model

import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

data class NewItem(

    @SerializedName("item_name")
    val itemName: String,

    @SerializedName("item_display_name")
    val itemDisplayName: String,

    @SerializedName("item_count")
    val itemCount: String,

    @SerializedName("auction_price_per_unit")
    val auctionPricePerUnit: String,

    @SerializedName("date_auction_expire")
    val dateAuctionExpire: ZonedDateTime,
)
