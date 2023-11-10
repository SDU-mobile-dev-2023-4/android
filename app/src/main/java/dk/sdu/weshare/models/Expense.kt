package dk.sdu.weshare.models

import com.fasterxml.jackson.annotation.JsonProperty

class Expense(
    @JsonProperty("title") val title: String,
    @JsonProperty("price") val price: Int,
    @JsonProperty("payerId") val payerId: Int,
)
