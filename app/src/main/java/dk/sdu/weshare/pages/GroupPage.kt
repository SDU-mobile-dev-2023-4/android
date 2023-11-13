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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.models.Group

data class GroupPageProps(
    val groupId: Int,
    val onBack: () -> Unit,
    val onEditGroup: (Int) -> Unit,
    val onAddExpense: (Int) -> Unit,
)

class GroupPagePropsProvider : PreviewParameterProvider<GroupPageProps> {
    private val fakeValues = listOf(
        GroupPageProps(1, {}, {}, {})
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

@Composable
fun CalculationList(group: Group) {

    val totalSpent = group.expenses!!.sumOf { it.price }
    //calculate total amount paid by user with id
    val ourSpent = group.expenses.filter { it.payerId == Auth.user!!.id }.sumOf { it.price }

    //calculate the amount of money each member should to pay
    val expectedSpent = totalSpent / group.members!!.size

    //calculate the amount of money the user owes
    val surplus = ourSpent - expectedSpent


    Column (
        modifier = Modifier
        .padding(start = 60.dp, end = 60.dp)
        ) {

        Row {
            Text(
                text = "${Auth.user!!.name}:",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            //show how much the user either ows or is owed
            if (surplus < 0) {
                Text(
                    text = "$surplus",
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
            } else {
                Text(
                    text =  "$surplus",
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                    color = Color.Green,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
            }
        }
        Row {
            Text(
                text = "Total: ",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(text = "$totalSpent",
                textAlign = TextAlign.End,
                fontSize = 30.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun ExpensesList(group: Group) {
    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        group.expenses!!.forEach { expense ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black)
                    .padding(all = 4.dp)
            ) {
                Text(text = group.members!!.find { it.id == expense.payerId }?.name ?: "Unknown user",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(0.6f))

                Text(text = expense.name,
                    fontSize = 20.sp, textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))

                Text(text = "${expense.price}",
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(0.6f)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GroupPage(
    @PreviewParameter(GroupPagePropsProvider::class) props: GroupPageProps
) {
    var group: Group by remember { mutableStateOf(Group(-1, "Loading...", listOf(), listOf())) }
    Api.getGroup(props.groupId) {
        if (it != null) {
            group = it
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier =Modifier.size(60.dp))
            Text( group.name,
                fontSize = 60.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "View Group ${group.name}",
                modifier = Modifier
                    .size(60.dp)
                    .clickable(onClick = { props.onEditGroup(props.groupId) })
            )
        }

        Spacer(modifier = Modifier.size(30.dp))
        CalculationList(group)

        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = { props.onAddExpense(props.groupId) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.border(2.dp,
                Color.Green,
                shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults
                .buttonColors(
                    contentColor = Color.Transparent,
                    containerColor = Color.Transparent
                )
        ){

            Text("Add",
                fontSize = 30.sp,
                color = Color.Green)
        }

        Spacer(modifier = Modifier.size(30.dp))
        // Integrate the ExpensesList Composable
        ExpensesList(group)
    }
}
