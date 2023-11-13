package dk.sdu.weshare.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.ui.theme.buttonGreen
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.authentication.Auth.Companion.user
import dk.sdu.weshare.fakeValues.Users

class SignInPageFunctionProvider : PreviewParameterProvider<(Int) -> Unit> {
    private val fakeValues: List<(Int) -> Unit> = listOf { }
    override val values: Sequence<(Int) -> Unit> = fakeValues.asSequence()
    override val count: Int = values.count()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SignInPage(
    @PreviewParameter(SignInPageFunctionProvider::class) onSignIn: () -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (isDialogOpen) {
        RegisterUserPopup(
            onRegisterUser = { email, name, password ->
                isDialogOpen = false
            }, onDismiss = {
                isDialogOpen = false
            }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)) {
        Text("UOMI", fontSize = 70.sp)
        Spacer(Modifier.size(48.dp))


        OutlinedTextField(
            value = email,
            onValueChange = { email = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next)

        )

        OutlinedTextField(
            password,
            onValueChange = { password = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )

        Spacer(Modifier.size(48.dp))
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    println("Sign in button clicked")
                    Auth().login(email, password) { loggedInUser ->
                        if (loggedInUser != null) {
                            println("User ${loggedInUser.id} logged in")
                            println("token: ${loggedInUser.token}")
                            onSignIn()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()) {
            Text("Sign in", fontSize = 30.sp)
        }
        Spacer(Modifier.size(48.dp))
        Text(
            "Register",
            fontSize = 30.sp,
            modifier = Modifier
                .clickable { isDialogOpen = true }
                .padding(start = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserPopup(onRegisterUser: (email: String, name: String, password: String) -> Unit, onDismiss: () -> Unit)
{
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            Spacer(Modifier.size(8.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = { onRegisterUser(email, name, password) }) {
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
