package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName


data class Group(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("users") val members: List<User>?,
    @SerializedName("expenses") val expenses: List<Expense>?,
)
