package dk.sdu.weshare.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.api.requests.RequestQueue
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.util.ServiceBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpensePage(
    groupId: Int,
    onSave: () -> Unit,
) {
    var group: Group? by remember { mutableStateOf(null) }

    Api.getGroup(groupId) {
        if (group == null) {
            println("Couldn't find group with id $groupId")
        }
        group = it
    }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("0") }
    var payer by remember { mutableStateOf(Auth.user) }

    var dropdownExpanded by remember { mutableStateOf(false) }

    fun saveExpense() {
        if (name.isNotEmpty()) {
            Api.addExpenseToGroup(groupId, Expense(payer!!.id, name, price.toInt())) {
                println(it)
                if (it != null) {
                    ServiceBuilder.invalidateCache()
                    onSave()
                } else {
                    println("Couldn't add expense to group, retrying...")
                    RequestQueue.addExpenseToQueue(groupId, Expense(payer!!.id, name, price.toInt()))
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)
    ) {
        OutlinedTextField(
            name,
            onValueChange = { name = it.filter { c -> c.isLetter() || c.isWhitespace() } },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next),
            colors =  TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.White,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
            )
        )
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color.Transparent),
                readOnly = true,
                value = payer!!.name,
                onValueChange = {},
                label = { Text("Payer") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                colors =  TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                )

            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier.background(Color(0xFF546d97))
            ) {
                group?.members?.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(
                            selectionOption.name,
                            fontSize = 20.sp,
                        ) },
                        onClick = {
                            payer = selectionOption
                            dropdownExpanded = false
                        },
                        modifier = Modifier.background(Color(0xFF546d97))
                    )
                }
            }
        }
        OutlinedTextField(
            price,
            onValueChange = { price = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { saveExpense() }
            ),
            colors =  TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.White,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
            )
        )

        Spacer(Modifier.size(48.dp))
        Button(
            onClick = {saveExpense()},
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
