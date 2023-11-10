package dk.sdu.weshare.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Group(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("member_ids") val memberIds: List<Int>,
)
