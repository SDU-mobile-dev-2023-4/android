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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.models.Group


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsPage(
    groupId: Int,
    onSave: () -> Unit,
    onBack: () -> Unit,
) {
    var group: Group? by remember { mutableStateOf(null) }
    var groupName by remember { mutableStateOf(group?.name ?: "") }
    Api.getGroup(groupId) {
        if (it != null) {
            group = it
        } else {
            println("Couldn't get group with id $groupId")
        }
    }
    var textColor by remember { mutableStateOf(Color.Black) }

    var isDialogOpen by remember { mutableStateOf(false) }

    if (isDialogOpen) {
        AddUserPopup(
            onAddUser = { email ->
                Api.addUserToGroup(group!!, email) {
                    if (it != null) {
                        group = it
                        isDialogOpen = false
                    }
                }
            }, onDismiss = {
                isDialogOpen = false
            }
        )
    }//     adding a top bar
    Column{
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            //turn back to groups page
            Icon(imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(start = 8.dp)
                    .clickable { onBack() }
            )
            //Group name
            Text(
                text = group?.name ?: "...",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier,
                color = textColor
            )
        }
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
            label = { Text("Name") },
            onValueChange = {
                groupName = it
                group = group?.copy(name = it) // Update the group object
                textColor = Color.Green
            },

            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
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
            group?.members?.forEach { member ->
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
        Button(onClick = {
            if (group != null) {
                Api.updateGroup(group!!, groupName) {
                    if (it != null) {
                        onSave()
                    }
                }
            }
        }) {
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
