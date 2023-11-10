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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.sdu.weshare.fakeValues.Groups

import dk.sdu.weshare.fakeValues.Users
class GroupsPagePropsProvider : PreviewParameterProvider<GroupsPageProps> {
    private val fakeValues = listOf(
        GroupsPageProps(1, {}, {}, {}),
    )
    override val values = fakeValues.asSequence()
    override val count: Int = values.count()
}

data class GroupsPageProps(
    val userId: Int,
    val onViewProfile: (Int) -> Unit,
    val onViewGroup: (Int) -> Unit,
    val onCreateGroup: () -> Unit,
)

@Composable
fun GroupsPage(
    @PreviewParameter(GroupsPagePropsProvider::class) props: GroupsPageProps,
) {

    val user = Users().getUsers().find { user -> user.id == props.userId }!!

    val groups = Groups().getGroups()

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
                    .clickable { props.onViewProfile(user.id) })
            Text(user.name, fontSize = 30.sp)
            Icon(imageVector = Icons.TwoTone.Add,
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { props.onCreateGroup() })
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
                                props.onViewGroup(group.id)
                            }) {
                        Text(group.name, fontSize = 30.sp)

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
