package dk.sdu.weshare

import androidx.compose.foundation.layout.Arrangement
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
fun GroupsPage(
    onViewProfile: (Int) -> Unit,
    onViewGroup: (Int) -> Unit,
    onCreateGroup: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
        Text("Groups", fontSize = 70.sp)
        Spacer(Modifier.size(48.dp))
        Button(onClick = { onViewProfile(6969) }) {
            Text("onViewProfile", fontSize = 30.sp)
        }
        Spacer(Modifier.size(16.dp))
        Button(onClick = { onViewGroup(64) }) {
            Text("onViewGroup", fontSize = 30.sp)
        }
        Spacer(Modifier.size(16.dp))
        Button(onClick = onCreateGroup) {
            Text("onCreateGroup", fontSize = 30.sp)
        }
    }
}
