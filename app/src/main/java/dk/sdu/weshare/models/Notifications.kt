package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName

data class Notifications(
    @SerializedName("notifications") val notifications: List<Notification>,
)
