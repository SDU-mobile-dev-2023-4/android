package dk.sdu.weshare.fakeValues

import dk.sdu.weshare.models.User

class Users {
	fun getUsers(): List<User>{
		return listOf(
			User(1, "Bengt", "bengt@live.dk", "MegetSikkertPassword"),
			User(2, "Per", "per@gmail.com", "MegetSikkertPassword"),
			User(3, "Jens", "jens@gmail.com", "MegetSikkertPassword"),
			User(4, "Mads", "mads@mail.com,", "MegetSikkertPassword")
		)
	}

	fun getRandomUser(): User {
		return getUsers().random()
	}

	fun getUserByEmail(email: String): User? {
		return getUsers().find { it.email == email }
	}
}
