package dk.sdu.weshare.fakeValues

import dk.sdu.weshare.models.Group


class Groups {
	private val fakeValues = listOf(
		Group(1, "Group1", listOf(1, 2)),
		Group(2, "Group2", listOf(2, 3)),
		Group(3, "Group3", listOf(1, 2, 3)),
		Group(4, "Group4", listOf(1, 4)),
	)
	fun getGroups() : List<Group>{
		return fakeValues
	}

	fun getGroupById(groupId: Int): Group? {
		return getGroups().find { it.id == groupId }
	}
}
