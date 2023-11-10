package dk.sdu.weshare.fakeValues

import java.time.LocalDateTime

data class UserPageProps (
	val id: Int,
	val name: String,
	val email: String,
	val password: String,
	val emailVerifiedAt: LocalDateTime,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
	val groupIds: List<Int>,
	val onBack: () -> Unit,
) {

}