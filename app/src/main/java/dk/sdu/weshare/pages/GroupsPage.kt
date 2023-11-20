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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.twotone.Add
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
fun GroupsPage(
    onViewProfile: () -> Unit,
    onViewGroup: (Int) -> Unit,
) {
    val user = Auth.user!!

    var groups: List<Group> by remember { mutableStateOf(listOf()) }
    Api.getAllGroups { groups = it ?: listOf()}

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
                    .clickable { onViewProfile() }
            )
            Text(user.name, fontSize = 30.sp)
            Icon(imageVector = Icons.TwoTone.Add,
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {
                        Api.createGroup("Unnamed group") {
                            if (it != null) {
                                onViewGroup(it.id)
                            }
                        }
                    })
        }
        Spacer(Modifier.size(32.dp))
        Text("Groups",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {

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
                        Text(group.name, fontSize = 30.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(0.9f))

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
