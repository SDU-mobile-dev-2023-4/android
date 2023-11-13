package dk.sdu.weshare.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GroupPageProps(
    val groupId: Int,
    val onBack: () -> Unit,
    val onEditGroup: (Int) -> Unit,
    val onAddExpense: (Int) -> Unit,
)

class GroupPagePropsProvider : PreviewParameterProvider<GroupPageProps> {
    private val fakeValues = listOf(
        GroupPageProps(1, {}, {}, {})
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

@Preview(showBackground = true)
@Composable
fun GroupPage(
    @PreviewParameter(GroupPagePropsProvider::class) props: GroupPageProps
) {
    return Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text("Group ${props.groupId}", fontSize = 70.sp)
        Spacer(Modifier.size(48.dp))



        Button(onClick = props.onBack) {
            Text("onBack", fontSize = 30.sp)
        }

        Spacer(Modifier.size(16.dp))
        Button(onClick = { props.onEditGroup(props.groupId) }) {
            Text("onEditGroup", fontSize = 30.sp)
        }
        Spacer(Modifier.size(16.dp))
        Button(onClick = { props.onAddExpense(props.groupId) }) {
            Text("onAddExpense", fontSize = 30.sp)
        }
    }
}
