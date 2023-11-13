package dk.sdu.weshare.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.fakeValues.Groups
import dk.sdu.weshare.fakeValues.Users

class GroupDetailsPagePropsProvider : PreviewParameterProvider<GroupDetailsPageProps> {

    private val fakeValues = listOf(
        GroupDetailsPageProps(null) { },
        GroupDetailsPageProps("1") { },
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

data class GroupDetailsPageProps(
    val groupId: String?,
    val onSave: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GroupDetailsPage(
    @PreviewParameter(GroupDetailsPagePropsProvider::class) props: GroupDetailsPageProps,
) {
    val group = Groups().getGroups().find { it.id == (props.groupId?.toInt() ?: -1) }!!

    var isDialogOpen by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf(group.name) }
    var members by remember { mutableStateOf(group.members!!) }

    if (isDialogOpen) {
        AddUserPopup(
            onAddUser = { email ->
                isDialogOpen = false
                val user = Users().getUserByEmail(email)
                if (user != null) {
                    members += user
                }

            }, onDismiss = {
                isDialogOpen = false
            }
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)
    ) {
        OutlinedTextField(
            value = groupName,
            onValueChange = { groupName = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(Modifier.size(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                "Members",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            Icon(imageVector = Icons.TwoTone.Add,
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(60.dp)
                    .border(1.dp, color = Color.Black)
                    .clickable { isDialogOpen = true })
        }

        Column {
            members.forEach { member ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(member.email, fontSize = 30.sp)
                }
            }
        }

        Spacer(Modifier.weight(1f))
        Button(onClick = props.onSave) {
            Text("Save", fontSize = 30.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserPopup(onAddUser: (email: String) -> Unit, onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add User") },
        text = {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = { onAddUser(email) }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}
