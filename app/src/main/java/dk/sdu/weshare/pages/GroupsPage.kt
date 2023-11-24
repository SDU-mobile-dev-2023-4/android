package dk.sdu.weshare.pages

import android.widget.ImageView
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import kotlinx.coroutines.delay

@Composable
fun GroupsPage(
    onViewProfile: () -> Unit,
    onViewGroup: (Int) -> Unit,
) {
    val user = Auth.user!!
    var isVisible by remember { mutableStateOf(false) }
    var groups: List<Group> by remember { mutableStateOf(listOf()) }
    Api.getAllGroups { groups = it ?: listOf()}
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { onViewProfile() },
                tint = Color.Green
            )
            Text(user.name, fontSize = 30.sp)
            Icon(imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.group_add),
                contentDescription = "onCreateGroup",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        Api.createGroup("Unnamed group", "") {
                            if (it != null) {
                                onViewGroup(it.id)
                            }
                        }
                    }, tint = Color.Green
            )
        }
        Divider( color = Color(0x80FFFFFF), thickness = 1.dp, modifier = Modifier.padding( top = 8.dp, bottom = 8.dp))
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


            groups.forEachIndexed { index, group ->
                var isVisible by remember { mutableStateOf(false) }
                LaunchedEffect(key1 = group) {
                    delay(index * 100L)  // Staggered delay for each group
                    isVisible = true
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)) +
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth },
                                animationSpec = tween(durationMillis = 300)
                            )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(start = 4.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color(0xFF2CF128)
                                    )
                                )
                            )
                            .clickable {
                                onViewGroup(group.id)
                            },
                        ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = dk.sdu.weshare.R.drawable.group_24),
                            contentDescription = "Group",
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color.White
                        )
                        Text(
                            group.name, fontSize = 30.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .weight(0.9f)
                                .padding(start = 8.dp)
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = "View Group ${group.name}",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            tint = Color(0xFF0d4369)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))

            }
        }
    }
}
