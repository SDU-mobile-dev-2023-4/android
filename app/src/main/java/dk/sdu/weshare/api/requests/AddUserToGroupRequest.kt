package dk.sdu.weshare.api.requests

import com.google.gson.annotations.SerializedName

data class AddUserToGroupRequest(
    @SerializedName("email") val userEmail: String,
)
