package dk.sdu.weshare.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.fakeValues.Users

class ProfilePagePropsProvider : PreviewParameterProvider<ProfilePageProps> {
    private val fakeValues = listOf(
        ProfilePageProps(1) { },
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

data class ProfilePageProps(
    val userId: Int,
    val onBack: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProfilePage(
    @PreviewParameter(ProfilePagePropsProvider::class) props: ProfilePageProps
) {

    val user = Users().getUsers().find { it.id == props.userId }!!

    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(192.dp)
        )
        Spacer(Modifier.size(48.dp))
        OutlinedTextField(
            name,
            onValueChange = { name = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            email,
            onValueChange = { email = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(48.dp))
        Button(
            onClick = props.onBack, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save", fontSize = 30.sp)
        }
    }
}
