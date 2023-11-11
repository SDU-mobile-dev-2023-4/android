package dk.sdu.weshare.authentication

import dk.sdu.weshare.api.RestApiService
import dk.sdu.weshare.data.LoginRequest
import dk.sdu.weshare.data.User

class AuthUser {

	// variable to store the token
	private var token: String? = null

	// function to get the token
	fun getToken(): String? {
		return token
	}

	//function to set the token
	fun setToken(token: String?) {
		if (token != null) {
			// set the token
			this.token = token
		}
	}

	fun login(email: String, password: String, onResult: (User?) -> Unit) {
		val apiService = RestApiService()
		val loginRequest = LoginRequest(email, password)
		apiService.login(loginRequest){
			if (it != null){
				setToken(it.token)

			}
			onResult(it)
		}
	}
}
