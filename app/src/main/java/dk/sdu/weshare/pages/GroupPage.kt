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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.twotone.Add
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.models.Group

@Composable
fun GroupPage(
    groupId: Int,
    onBack: () -> Unit,
    onEditGroup: (Int) -> Unit,
    onAddExpense: (Int) -> Unit,
) {
    var group: Group? by remember { mutableStateOf(null) }
    Api.getGroup(groupId) {
        if (it != null) {
            group = it
        }
    }
//     adding a top bar
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            //turn back to groups page
            Icon(imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)

                    .clickable { onBack() }
            )
            //Group name
            Text( group?.name ?: "...",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.8f),
            )
            // edit group
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "View Group ${group?.name ?: "..."}",
                modifier = Modifier
                    .size(60.dp)
                    .clickable(onClick = { onEditGroup(groupId) })
            )
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
            Spacer(modifier = Modifier.size(60.dp))
        }

        Spacer(modifier = Modifier.size(30.dp))
        group?.let { CalculationList(it) }

        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = { onAddExpense(groupId) },
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
        group?.let { ExpensesList(it) }
    }
}

@Composable
fun CalculationList(group: Group) {

    val totalExpenses = group.expenses!!.sumOf { it.price }
    val ourExpenses = group.expenses
        .filter { it.payerId == Auth.user!!.id } // only get our expenses
        .sumOf { it.price }

    // calculate the amount of money each member should pay
    val expectedShare = totalExpenses / group.members!!.size

    // calculate the amount of money the user owes
    val surplus = ourExpenses - expectedShare

    Column (
        modifier = Modifier.padding(start = 60.dp, end = 60.dp)
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
            Text(text = "$totalExpenses",
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
