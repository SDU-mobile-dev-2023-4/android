package dk.sdu.weshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshare.pages.CreateExpensePage
import dk.sdu.weshare.pages.CreateExpensePageProps
import dk.sdu.weshare.pages.GroupDetailsPage
import dk.sdu.weshare.pages.GroupDetailsPageProps
import dk.sdu.weshare.pages.GroupPage
import dk.sdu.weshare.pages.GroupPageProps
import dk.sdu.weshare.pages.GroupsPage
import dk.sdu.weshare.pages.GroupsPageProps
import dk.sdu.weshare.pages.ProfilePage
import dk.sdu.weshare.pages.ProfilePageProps
import dk.sdu.weshare.pages.SignInPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {
                    SignInPage { navController.navigate("groups") }
                }
                composable("groups") {
                    GroupsPage(
                        GroupsPageProps(
                            onViewProfile = {
                                navController.navigate("profile")
                            },
                            onViewGroup = { groupId: Int ->
                                navController.navigate("group/$groupId")
                            },
                            onCreateGroup = {
                                navController.navigate("group_details")
                            }
                        )
                    )
                }
                composable("profile") {
                    ProfilePage(
                        ProfilePageProps {
                            navController.navigate("groups")
                        }
                    )
                }
                composable(
                    "group/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    GroupPage(
                        GroupPageProps(
                            navBackStackEntry.arguments?.getInt("groupId")!!,
                            onBack = {
                                navController.navigate("groups")
                            },
                            onEditGroup = { groupId ->
                                navController.navigate("group_details?groupId=$groupId")
                            },
                            onAddExpense = { groupId ->
                                navController.navigate("create_expense/$groupId")
                            },
                        )
                    )
                }

                composable(
                    "group_details?groupId={groupId}",
                    arguments = listOf(navArgument("groupId") {
                        nullable = true
                    })
                ) { navBackStackEntry ->
                    GroupDetailsPage(
                        GroupDetailsPageProps(
                            navBackStackEntry.arguments?.getString("groupId"),
                            onSave = {
                                navController.navigate("group/${navBackStackEntry.arguments?.getInt("groupId")!!}")
                            },
                        )
                    )
                }
                composable(
                    "create_expense/{groupId}", arguments = listOf(
                        navArgument("groupId") {
                            type = NavType.IntType
                        },
                    )
                ) { navBackStackEntry ->
                    CreateExpensePage(
                        CreateExpensePageProps(
                            navBackStackEntry.arguments?.getInt("groupId")!!,
                            onSave = { navController.navigate("group/${navBackStackEntry.arguments?.getInt("groupId")!!}") },
                        )
                    )
                }
            }
        }
    }
}
