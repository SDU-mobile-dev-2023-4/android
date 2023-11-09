package dk.sdu.weshare.fakeValues

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import java.time.LocalDateTime

class GroupPagePropsProvider : PreviewParameterProvider<GroupPageProps> {
	private val fakeValues = listOf(
		GroupPageProps(1, "Group1", LocalDateTime.now(), LocalDateTime.now()) { },
		GroupPageProps(2, "Group2", LocalDateTime.now(), LocalDateTime.now()) { },
		GroupPageProps(3, "Group3", LocalDateTime.now(), LocalDateTime.now()) { },
		GroupPageProps(4, "Group4", LocalDateTime.now(), LocalDateTime.now()) { },
		GroupPageProps(5, "Group5", LocalDateTime.now(), LocalDateTime.now()) { },

	)
	override val values = fakeValues.asSequence()
	override val count: Int = values.count()
}