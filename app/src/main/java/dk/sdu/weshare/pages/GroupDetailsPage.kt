package dk.sdu.weshare.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.util.ServiceBuilder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsPage(
    groupId: Int,
    onSave: () -> Unit,
    onBack: () -> Unit,
) {
    var group: Group? by remember { mutableStateOf(null) }
    var groupName by remember { mutableStateOf("")}
    var groupDescription by remember { mutableStateOf("") }
    var changed by remember { mutableStateOf(false) }

    Api.getGroup(groupId) {
        if (it != null) {
            group = it
            // Only update groupName if it hasn't been changed by the user
            if (!changed) {
                groupName = it.name
                groupDescription = it.description
            }
        } else {
            println("Couldn't get group with id $groupId")
        }
    }
    var isDialogOpen by remember { mutableStateOf(false) }

    if (isDialogOpen) {
        AddUserPopup(
            onAddUser = { email ->
                ServiceBuilder.invalidateCache()
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
                    .clickable {
                        if (changed) {
                            ServiceBuilder.invalidateCache()
                        }
                        onBack()
                    },
                tint = Color.Green
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
            value = if (changed) groupName else group?.name ?: "",
            label = { Text("Name") },
            onValueChange = {
                groupName = it
                changed = true
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        ) // groupName

        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = if (changed) groupDescription else group?.description ?: "",
            label = { Text("Description") },
            onValueChange = {
                groupDescription = it
                changed = true
            },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )// groupDescription
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
            )

            Icon(imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.person_add_24),
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .padding(start = 26.dp, bottom = 16.dp, top = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { isDialogOpen = true },
                tint = Color.Green
            )
        }

        Column{
            group?.members?.forEach { member ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.person_24),
                        contentDescription = "Group",
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color.White
                    )
                    Text(
                        member.email, fontSize = 15.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Start,

                    )
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove group member",
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .clickable {
                                Api.removeUserFromGroup(group!!, member) {
                                    if (it != null) {
                                        group = it
                                    }
                                }
                                ServiceBuilder.invalidateCache()
                            },
                        tint = Color.Red // Apply the red tint color here
                    )

                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
            if (group != null) {
                if (!changed) {
                    onSave()
                } else {
                    ServiceBuilder.invalidateCache()
                    Api.updateGroup(group!!, groupName, groupDescription) {
                        if (it != null) {
                            onSave()
                        }
                    }
                }
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
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
        containerColor = Color(0xA1112b5d),
        modifier = Modifier
            .border(6.dp, Brush.verticalGradient(
                colors = listOf(Color(0x00112b5d), Color(0xA0112b5d)),
                startY = 0f,
                endY = 500f
            ), RoundedCornerShape(26.dp)),
        text = {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "User Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xA1112b5d)),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    containerColor = Color(0xA0112b5d),
                ),
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
