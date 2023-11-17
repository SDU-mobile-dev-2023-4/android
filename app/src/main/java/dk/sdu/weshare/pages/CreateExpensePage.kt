package dk.sdu.weshare.pages

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group

class CreateExpensePagePropsProvider : PreviewParameterProvider<CreateExpensePageProps> {
    private val fakeValues = listOf(
        CreateExpensePageProps(1) {},
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

data class CreateExpensePageProps(
    val groupId: Int,
    val onSave: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CreateExpensePage(
    @PreviewParameter(CreateExpensePagePropsProvider::class) props: CreateExpensePageProps
) {
    var group: Group? by remember { mutableStateOf(null) }

    Api.getGroup(props.groupId) {
        if (group == null) {
            println("Couldn't find group with id ${props.groupId}")
        }
        group = it
    }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var payer by remember { mutableStateOf(Auth.user) }

    var dropdownExpanded by remember { mutableStateOf(false) }
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
            onValueChange = { name = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            price,
            onValueChange = { price = it.filter { c -> !c.isWhitespace() } },
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next)
        )

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                readOnly = true,
                value = payer!!.name,
                onValueChange = {},
                label = { Text("Payer") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
            ) {
                group?.members?.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.name) },
                        onClick = {
                            payer = selectionOption
                            dropdownExpanded = false
                        },
                    )
                }
            }
        }

        Spacer(Modifier.size(48.dp))
        Button(
            onClick = {
                Api.addExpenseToGroup(props.groupId, Expense(payer!!.id, name, price.toInt())) {
                    println(it)
                    if (it != null) {
                        props.onSave()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Black), CircleShape)
                .clip(RoundedCornerShape(5.dp))
        ) {
            Text("Save", fontSize = 30.sp)
        }
    }
}
