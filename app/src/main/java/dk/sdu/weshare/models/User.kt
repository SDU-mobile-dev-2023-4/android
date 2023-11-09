package dk.sdu.weshare.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name")val name: String,
    @JsonProperty("email")val email: String,
    @JsonProperty("email_verified_at")val emailVerifiedAt: LocalDateTime,
    @JsonProperty("created_at")val createdAt: LocalDateTime,
    @JsonProperty("updated_at")val updatedAt: LocalDateTime,
    @JsonProperty("group_ids")val groupIds: List<Int>,
)
