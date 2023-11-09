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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.Model.Group

@Composable
fun GroupsPage(
    onViewProfile: (Int) -> Unit,
    onViewGroup: (Int) -> Unit,
    onCreateGroup: () -> Unit,
) {
    val groups = listOf(
        Group(1, "Group 1"),
        Group(2, "Group 2"),
        Group(3, "Group 3"),
        Group(4, "Group 4"),
    )

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { onViewProfile(6969) })
            Text("user.name", fontSize = 30.sp)

            Icon(imageVector = Icons.TwoTone.Add,
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { onCreateGroup() })
        }


        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Text("Groups", fontSize = 36.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.size(16.dp))

            Column {
                groups.forEach { group ->
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, color = Color.Black)
                            .padding(start = 16.dp)
                            .clickable {
                                onViewGroup(group.id)
                            }) {
                        Text("Group ${group.name}", fontSize = 30.sp)

                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = "View Group ${group.name}",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)

                        )
                    }
                }
            }
        }
    }
}
