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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.fakeValues.Groups
import dk.sdu.weshare.fakeValues.Users
import dk.sdu.weshare.models.Expense

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
fun CalculationList(expenses: MutableList<Expense>) {

    //call api to get users of the group
    val payer = Users().getUsers()

    //call api to get user
    val user = Users().getRandomUser()

    // calculate total amount of money used
    var total = 0
    expenses.forEach { expense ->
        total += expense.price
    }

    //calculate total amount payed by user with id
    val payed = expenses.filter { it.payerId == user.id }.sumOf { it.price }

    //calculate the amount of money each person has to pay
    val amountPrPers = total / payer.size

    //calculate the amount of money user owe
    val owe = mutableListOf<Int>()
    for (i in 0 until payer.size) {
        owe.add(amountPrPers)
    }
    Column (
        modifier = Modifier
        .padding(60.dp, 24.dp)
        ) {
        Row() {
            Text(
                text = "${payer.find { it.id == user.id }!!.name}:",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            //show how much the user either ows or is owed
            if (payed > owe[0]) {
                Text(
                    text =  "${(payed - owe[0])}",
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
            } else {
                Text(
                    text =  "${(owe[0] - payed)}",
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                    color = Color.Green,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
            }
        }
        Row {
            Text(
                text = "Total: ",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(text = "${total}",
                textAlign = TextAlign.End,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun ExpensesList(expenses: List<Expense>) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        val payer = Users().getUsers()

        // Display each expense in the list
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            expenses.forEach { expense ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = Color.Black)
                        .padding(all = 4.dp)
                ) {

                    Text(text = payer.find { it.id == expense.payerId }!!.name,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f))

                    Text(text = expense.name,
                        fontSize = 16.sp, textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f))

                    Text(text = "${expense.price}",
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f))
                }
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun GroupPage(
    @PreviewParameter(GroupPagePropsProvider::class) props: GroupPageProps
) {

    // Sample list of expenses
    val expensesList = mutableListOf<Expense>()
    expensesList.add(Expense("Expense 1", 12121, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 2", 23320, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 3", 34052, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 4", 40000, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 5", 50002, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 6", 60020, Users().getRandomUser().id))
    expensesList.add(Expense("Expense 7", 70090, Users().getRandomUser().id))


    val group = Groups().getGroups().find { it.id == props.groupId }!!

    group.name

    return Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .clickable(onClick = { props.onEditGroup(props.groupId) })
    ) {
        Text( group.name, fontSize = 70.sp)
//        Spacer(Modifier.size(30.dp))

        CalculationList(expensesList)

//        Spacer(Modifier.size(16.dp))
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
        // Integrate the ExpensesList Composable
        ExpensesList(expensesList)
    }
}

