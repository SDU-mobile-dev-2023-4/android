package dk.sdu.weshare.api

import com.google.gson.annotations.SerializedName

data class LoginCredentials(
    val email: String,
    val password: String,
    @SerializedName("device_name") val deviceName: String,
)
