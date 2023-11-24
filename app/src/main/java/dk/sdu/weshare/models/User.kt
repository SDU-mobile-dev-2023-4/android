package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String?,
)
