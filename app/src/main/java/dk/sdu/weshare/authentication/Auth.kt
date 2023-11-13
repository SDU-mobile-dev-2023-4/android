package dk.sdu.weshare.authentication

import dk.sdu.weshare.api.ApiService
import dk.sdu.weshare.models.LoginCredentials
import dk.sdu.weshare.models.User

class Auth {
	companion object {
		private var _user: User? = null
		var user: User?
			get() { return _user }
			private set(value) { _user = value }
	}

	fun login(email: String, password: String, onResult: (User?) -> Unit) {
		println("called login() method")
		val apiService = ApiService()
		val loginRequest = LoginCredentials(email, password)
		apiService.login(loginRequest) {
			println("callback")
			if (it != null) {
				user = it
			}
			onResult(it)
		}
	}
}
