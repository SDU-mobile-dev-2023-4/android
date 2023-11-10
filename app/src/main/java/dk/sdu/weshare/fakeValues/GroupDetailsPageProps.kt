package dk.sdu.weshare.fakeValues

data class GroupDetailsPageProps(
	val groupId: String?,
	val onSave: (Int) -> Unit,
)
