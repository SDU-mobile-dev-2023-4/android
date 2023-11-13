package dk.sdu.weshare.authentication

import dk.sdu.weshare.api.Api
import dk.sdu.weshare.models.User

class Auth {
	companion object {
		var user: User? = null
			private set
	}

	fun register(name: String, email: String, password: String, onResult: (User?) -> Unit) {
		Api.register(name, email, password) {
			if (it != null) {
				user = it
			}
			onResult(it)
		}
	}

	fun login(email: String, password: String, onResult: (User?) -> Unit) {
		Api.login(email, password) {
			if (it != null) {
				user = it
			}
			onResult(it)
		}
	}
}
