package dk.sdu.weshare.fakeValues

data class ExpenseDetailsPageProps(
	val groupId: Int,
	val expenseId: String?,
	val onSave: () -> Unit,
)