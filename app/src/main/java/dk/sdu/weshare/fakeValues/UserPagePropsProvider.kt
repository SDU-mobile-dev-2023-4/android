package dk.sdu.weshare.fakeValues

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import java.time.LocalDateTime


class UserPagePropsProvider: PreviewParameterProvider<UserPageProps> {
	private val fakeValues = listOf(
		UserPageProps(1,"spiller1", "email1@gmail.com","spiller1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), listOf(2,3,4)) { },
		UserPageProps(2,"spiller2", "email2@gmail.com","spiller2",  LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), listOf(1,3,4)) { },
		UserPageProps(3,"spiller3", "email3@gmail.com", "spiller3", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), listOf(1)) { },
		UserPageProps(4,"spiller4", "email4@gmail.com", "spiller4", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), listOf(2)) { },
		UserPageProps(5,"spiller5", "email5@gmail.com", "spiller5",  LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), listOf(4,3)) { },

	)
	override val values = fakeValues.asSequence()
	override val count: Int = values.count()
}