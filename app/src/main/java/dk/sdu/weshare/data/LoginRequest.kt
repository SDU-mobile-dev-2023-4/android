package dk.sdu.weshare.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
	@SerializedName("email")val email: String?,
	@SerializedName("password")val password: String?,
	@SerializedName("device_name")val deviceName: String = "Android"
)
