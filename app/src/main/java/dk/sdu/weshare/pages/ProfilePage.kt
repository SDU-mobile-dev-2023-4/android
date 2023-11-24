package dk.sdu.weshare.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.authentication.Auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    onSave: () -> Unit
) {

    val user = Auth.user!!

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
            modifier = Modifier.size(192.dp),
            tint = Color.White
        )
        Spacer(Modifier.size(48.dp))
        // User's name
        OutlinedTextField(
            name,
            onValueChange = { name = it.filter { c -> c.isLetter() || c.isWhitespace() } },
            maxLines = 1,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    Auth.user!!.name = name;
                    onSave()
                }
            ),
            colors =  TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.White,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
            )
        )
        // User's email
        OutlinedTextField(
            email,
            onValueChange = { email = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            colors =  TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.White,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                containerColor = Color(0x66FFFFFF)
            )
        )
        Spacer(Modifier.size(48.dp))
        // This should update the user's name, but there is no API for that
        Button(
            onClick = {
                onSave()
              },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Save", fontSize = 30.sp)
        }
    }
}
