package dk.sdu.weshare.api.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String,
    @SerializedName("device_name") val deviceName: String,
)
