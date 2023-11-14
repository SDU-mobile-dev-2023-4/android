package dk.sdu.weshare.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dk.sdu.weshare.ui.theme.buttonGreen
import dk.sdu.weshare.authentication.Auth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInPage(
    navController: NavController, // Add NavController parameter
    onSignIn: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        Spacer(Modifier.size(16.dp))
        // Add the Register button here
        Row {
            Text(text = "Don't have an account?")
            Spacer(Modifier.size(4.dp))
            Text(text = "Register", color = buttonGreen, modifier = Modifier
                .padding(start = 4.dp)
                .clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}