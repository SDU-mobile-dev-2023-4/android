package dk.sdu.weshare.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Group(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("created_at")val createdAt: LocalDateTime,
    @JsonProperty("updated_at")val updatedAt: LocalDateTime,
)
