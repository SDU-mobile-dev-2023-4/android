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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val fakeValues = listOf(
    ExpenseDetailsPageProps(1, null) { },
//    ExpenseDetailsPageProps(1, "2") { },
)

class ExpenseDetailsPagePropsProvider : PreviewParameterProvider<ExpenseDetailsPageProps> {
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

data class ExpenseDetailsPageProps(
    val groupId: Int,
    val expenseId: String?,
    val onSave: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ExpenseDetailsPage(
    @PreviewParameter(ExpenseDetailsPagePropsProvider::class) props: ExpenseDetailsPageProps
) {
    val possiblePayers = listOf("Jakob", "Jon", "Michael")

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedPayer by remember { mutableStateOf(possiblePayers[0]) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(48.dp)
    ) {
        OutlinedTextField(
            title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        OutlinedTextField(
            price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                readOnly = true,
                value = selectedPayer,
                onValueChange = {},
                label = { Text("Payer") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                possiblePayers.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedPayer = selectionOption
                            expanded = false
                        },
                    )
                }
            }
        }

        Spacer(Modifier.size(48.dp))
        Button(
            onClick = props.onSave, colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, contentColor = Color.Black
            ), modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(2.dp, Color.Black), CircleShape
                )
                .clip(RoundedCornerShape(5.dp))
        ) {
            Text("Save", fontSize = 30.sp)
        }
    }
}
