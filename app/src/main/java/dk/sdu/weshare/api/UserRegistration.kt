package dk.sdu.weshare.api

import com.google.gson.annotations.SerializedName

data class UserRegistration(
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("device_name") val deviceName: String,
)
