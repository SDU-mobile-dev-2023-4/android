package dk.sdu.weshare.fakeValues

import dk.sdu.weshare.models.Group


class Groups {
	private val groups = mutableListOf<Group>()

	private fun feedGroups() {
		for (group in GroupPagePropsProvider().values) {
			val newGroup = Group(group.id, group.name, group.createdAt, group.updatedAt)
			// Customize the new group's properties as needed
			groups.add(newGroup)
		}
	}
	fun getGroups() : MutableList<Group>{
		feedGroups()
		return groups
	}

}