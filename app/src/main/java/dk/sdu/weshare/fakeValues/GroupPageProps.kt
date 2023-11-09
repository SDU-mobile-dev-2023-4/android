package dk.sdu.weshare.fakeValues


import java.time.LocalDateTime

data class GroupPageProps(
	val id: Int,
	val name: String,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
	val function: () -> Unit,
)