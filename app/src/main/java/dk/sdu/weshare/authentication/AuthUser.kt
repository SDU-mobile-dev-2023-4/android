package dk.sdu.weshare.authentication

import dk.sdu.weshare.fakeValues.Users
import dk.sdu.weshare.models.User

class AuthUser{
	fun login(email: String, password: String): Int {
		var users = Users().getUsers()
		var user = users.find { it.email == email && it.password == password }
		return user?.id ?: -1
	}
}