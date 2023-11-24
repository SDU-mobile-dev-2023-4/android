package dk.sdu.weshare.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
                    .clickable { onBack() },
                tint = Color.Green
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
                    .clip(CircleShape)
                    .clickable(onClick = { onEditGroup(groupId) }),
                tint = Color.Green
            )
        }
        Divider( color = Color(0x80FFFFFF), thickness = 1.dp, modifier = Modifier.padding( top = 8.dp, bottom = 8.dp))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        group?.let { CalculationList(it) }

        Spacer(modifier = Modifier.size(30.dp))
        Row {


            Button(
                onClick = { onAddExpense(groupId) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(
                    2.dp,
                    Color.Green,
                    shape = RoundedCornerShape(8.dp)
                ),
                colors = ButtonDefaults
                    .buttonColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent
                    ),
                contentPadding = PaddingValues(16.dp, 0.dp, 8.dp, 0.dp)
            ) {
                Text(
                    "Add",
                    fontSize = 30.sp,
                    color = Color.Green
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.attach_money_24),
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(40.dp),
                    tint = Color.Green
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            Button(
                onClick = {
                    if (group != null) {
                        Api.notifyGroup(group!!) {
                            if (it != null) {
                                println("Group notified")
                            }
                            println("Failed to notify group")
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(
                    2.dp,
                    Color.Yellow,
                    shape = RoundedCornerShape(8.dp)
                ),
                colors = ButtonDefaults
                    .buttonColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent
                    ),
                contentPadding = PaddingValues(16.dp, 0.dp, 8.dp, 0.dp)
            ) {
                Text(text = "Notify",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.notification_add_24),
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(35.dp),
                    tint = Color.Yellow
                )
            }
        }
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
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(start = 16.dp)
    ){
    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
            .border(2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {

            group.expenses!!.forEach { expense ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, color = Color.White)
                        .padding(all = 8.dp)
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
}
