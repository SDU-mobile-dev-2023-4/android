package dk.sdu.weshare.fakeValues

import dk.sdu.weshare.models.User

class Users {

	private val users = mutableListOf<User>()
	private fun feedUsers() {
		for (user in UserPagePropsProvider().values) {
			val newUser = User(
				user.id,
				user.name,
				user.email,
				user.password,
				user.emailVerifiedAt,
				user.createdAt,
				user.updatedAt,
				user.groupIds,
			)
			// Customize the new users's properties as needed
			users.add(newUser)
		}
	}
	fun getUsers() : MutableList<User>{
		feedUsers()
		return users
	}

}