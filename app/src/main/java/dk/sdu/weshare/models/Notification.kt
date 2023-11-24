package dk.sdu.weshare.models

import com.google.gson.annotations.SerializedName
import dk.sdu.weshare.models.notifications.RemovedFromGroup
import dk.sdu.weshare.models.notifications.*

data class Notification(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("notifiable_id") val notifiableId: Int,
    @SerializedName("notifiable_type") val notifiableType: String,
    // Data format, is either Added To Group, Removed From Group, Expense Added, but Kotlin doesn't really support it.
    @SerializedName("data") val data: Any,
    @SerializedName("read_at") val readAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)
