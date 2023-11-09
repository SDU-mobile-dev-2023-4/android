package dk.sdu.weshare.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UserIdProvider(override val values: Sequence<Int>) : PreviewParameterProvider<Int>
class OnBackProvider(override val values: Sequence<() -> Unit>) :
    PreviewParameterProvider<() -> Unit>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    @PreviewParameter(UserIdProvider::class) userId: Int,
    @PreviewParameter(OnBackProvider::class) onBack: () -> Unit,
) {
    var name by remember { mutableStateOf("The user's name should go here") }
    var phone by remember { mutableStateOf("And their phone should be here") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(Modifier.size(48.dp))
        OutlinedTextField(
            name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            phone,
            onValueChange = { phone = it },
            label = { Text("Phone") }
        )
        Spacer(Modifier.size(48.dp))
        Button(onClick = onBack) {
            Text("Save", fontSize = 30.sp)
        }
    }
}
