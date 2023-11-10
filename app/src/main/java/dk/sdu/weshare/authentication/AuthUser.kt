package dk.sdu.weshare.authentication

import dk.sdu.weshare.fakeValues.Users
import dk.sdu.weshare.models.User

class AuthUser {
	fun login(email: String, password: String): User? {
		val users = Users().getUsers()
		return users.find { it.email == email && it.password == password }
	}
}
