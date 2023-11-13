package dk.sdu.weshare.fakeValues

import dk.sdu.weshare.models.Group


class Groups {
	private val fakeValues = listOf(
		Group(1, "Group1", listOf(Users().getRandomUser(), Users().getRandomUser(), Users().getRandomUser()), null),
		Group(2, "Group2", listOf(Users().getRandomUser(), Users().getRandomUser(), Users().getRandomUser()), null),
		Group(3, "Group3", listOf(Users().getRandomUser(), Users().getRandomUser(), Users().getRandomUser()), null),
		Group(4, "Group4", listOf(Users().getRandomUser(), Users().getRandomUser(), Users().getRandomUser()), null),
	)
	fun getGroups() : List<Group>{
		return fakeValues
	}

	fun getGroupById(groupId: Int): Group? {
		return getGroups().find { it.id == groupId }
	}
}
