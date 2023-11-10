package dk.sdu.weshare.fakeValues

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class GroupDetailsPagePropsProvider : PreviewParameterProvider<GroupDetailsPageProps> {

	private val fakeValues = listOf(
		GroupDetailsPageProps(null) { },
		GroupDetailsPageProps("1") { },
	)
	override val values = fakeValues.asSequence()
	override val count: Int = values.count()
}