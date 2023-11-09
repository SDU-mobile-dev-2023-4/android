package dk.sdu.weshare.pages

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsPage(
    groupId: String?,
    onSave: (Int) -> Unit,
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    var groupname by remember { mutableStateOf("") }
    var members by remember { mutableStateOf(listOf<String>()) }

    if (isDialogOpen) {
        AddUserPopup(
            onAddUser = { email ->
                members += email
                isDialogOpen = false
            }, onDismiss = {
                isDialogOpen = false
            }
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)) {


        OutlinedTextField(
            value = groupname,
            onValueChange = { groupname = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Members", fontSize = 30.sp)
            Icon(imageVector = Icons.TwoTone.Add,
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { isDialogOpen = true })
        }

        Column {
            members.forEach { member ->
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(member, fontSize = 30.sp)
                }
            }
        }


        Spacer(Modifier.size(48.dp))
        Button(onClick = { onSave(groupId?.toInt() ?: 69) }) {
            Text("Save", fontSize = 30.sp)
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserPopup(onAddUser: (email: String) -> Unit, onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { /* Handle dialog dismissal */ },
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
            Button(
                onClick = { onAddUser(email) },
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
