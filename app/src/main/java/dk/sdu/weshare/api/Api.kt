package dk.sdu.weshare.api

import dk.sdu.weshare.models.LoginCredentials
import dk.sdu.weshare.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

	@POST("/api/login")
	fun login(@Body loginRequest: LoginCredentials): Call<User>
}
