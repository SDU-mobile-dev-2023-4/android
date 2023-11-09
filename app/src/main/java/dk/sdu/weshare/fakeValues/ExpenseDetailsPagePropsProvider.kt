package dk.sdu.weshare.fakeValues

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
class ExpenseDetailsPagePropsProvider : PreviewParameterProvider<ExpenseDetailsPageProps> {
	val fakeValues = listOf(
		ExpenseDetailsPageProps(1, null) { },
//    ExpenseDetailsPageProps(1, "2") { },
	)
	override val values = fakeValues.asSequence()
	override val count: Int = values.count()
}