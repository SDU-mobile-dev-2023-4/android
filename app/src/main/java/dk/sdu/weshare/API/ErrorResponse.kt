package dk.sdu.weshare.API

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
	@JsonProperty("message") val message: String
)