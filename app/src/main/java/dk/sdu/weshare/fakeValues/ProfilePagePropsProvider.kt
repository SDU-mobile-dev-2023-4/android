package dk.sdu.weshare.fakeValues

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
class ProfilePagePropsProvider : PreviewParameterProvider<ProfilePageProps> {
	private val fakeValues = listOf(
		ProfilePageProps(1) { },
		ProfilePageProps(2) { },
		ProfilePageProps(3) { },
		ProfilePageProps(4) { },
		ProfilePageProps(5) { },
	)
	override val values = fakeValues.asSequence()
	override val count: Int = values.count()
}