package dk.sdu.weshare.api

import dk.sdu.weshare.models.LoginCredentials
import dk.sdu.weshare.models.User
import dk.sdu.weshare.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiService {
	fun login(loginRequest: LoginCredentials, onResult: (User?) -> Unit) {
		val retrofit = ServiceBuilder.buildService(Api::class.java)
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
	}
}
