package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName


data class GroupSummary(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
)
