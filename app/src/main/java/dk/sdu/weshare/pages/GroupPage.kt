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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupPage(
    groupId: Int,
    onBack: () -> Unit,
    onEditGroup: (Int) -> Unit,
    onAddExpense: (Int) -> Unit,
    onViewExpense: (Int, Int) -> Unit,
) {
    return Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text("Group $groupId", fontSize = 70.sp)
        Spacer(Modifier.size(48.dp))



        Button(onClick = onBack) {
            Text("onBack", fontSize = 30.sp)
        }

        Spacer(Modifier.size(16.dp))
        Button(onClick = { onEditGroup(groupId) }) {
            Text("onEditGroup", fontSize = 30.sp)
        }
        Spacer(Modifier.size(16.dp))
        Button(onClick = { onAddExpense(groupId) }) {
            Text("onAddExpense", fontSize = 30.sp)
        }
        Spacer(Modifier.size(16.dp))
        Button(onClick = { onViewExpense(groupId, 420) }) {
            Text("onViewExpense", fontSize = 30.sp)
        }
    }
}
