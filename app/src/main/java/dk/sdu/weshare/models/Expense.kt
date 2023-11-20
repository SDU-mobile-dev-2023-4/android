package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("payer_id") val payerId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
)
