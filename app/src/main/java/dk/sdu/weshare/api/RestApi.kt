package dk.sdu.weshare.api

import dk.sdu.weshare.data.LoginRequest
import dk.sdu.weshare.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {

	@Headers("Accept: application/json", "Content-Type: application/json")
	@POST("/api/login")
	fun login(@Body loginRequest: LoginRequest): Call<User>
}