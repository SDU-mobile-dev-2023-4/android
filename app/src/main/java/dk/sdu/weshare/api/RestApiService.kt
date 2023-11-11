package dk.sdu.weshare.api

import dk.sdu.weshare.data.LoginRequest
import dk.sdu.weshare.data.User
import dk.sdu.weshare.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
	fun login(loginRequest: LoginRequest, onResult: (User?) -> Unit): User? {
		val retrofit = ServiceBuilder.buildService(RestApi::class.java)
		retrofit.login(loginRequest).enqueue(
			object : Callback<User> {
				override fun onFailure(call: Call<User>, t: Throwable) {
					onResult(null)
				}

				override fun onResponse(call: Call<User>, response: Response<User>) {
					onResult(response.body())
				}
			}
		)
		return null
	}

}