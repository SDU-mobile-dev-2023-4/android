package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName

data class LoginCredentials(
	@SerializedName("email") val email: String,
	@SerializedName("password") val password: String,
	@SerializedName("device_name") val deviceName: String = "Android"
)
