package dk.sdu.weshare.data

import com.google.gson.annotations.SerializedName

data class User(
	@SerializedName("id")val id: Int ?,
	@SerializedName("name")val name: String?,
	@SerializedName("email")val email: String?,
	@SerializedName("email_verified_at")val emailVerifiedAt: String?,
	@SerializedName("created_at")val createdAt: String?,
	@SerializedName("updated_at")val updatedAt: String?,
	@SerializedName("token")val token: String?
)
