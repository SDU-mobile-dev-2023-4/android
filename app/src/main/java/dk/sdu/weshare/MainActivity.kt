package dk.sdu.weshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshare.pages.ExpenseDetailsPage
import dk.sdu.weshare.pages.ExpenseDetailsPageProps
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
                    SignInPage(onSignIn = {userId:Int ->
                        navController.navigate("groups/${userId}")
                    })
                }
                composable(
                        "groups/{userId}",
                        arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    GroupsPage(
                        GroupsPageProps(
                            userId = navBackStackEntry.arguments!!.getInt("userId"),
                            onViewProfile = { userId: Int ->
                                navController.navigate("profile/$userId")
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
                composable(
                    "profile/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    ProfilePage(
                        ProfilePageProps(
                            navBackStackEntry.arguments?.getInt("userId")!!,
                            onBack = {
                                navController.navigate("groups/${navBackStackEntry.arguments?.getInt("userId")!!}")
                            },
                        )
                    )
                }
                composable(
                    "group/{groupId}",
                    arguments = listOf(navArgument("groupId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    GroupPage(
                        GroupPageProps(
                            navBackStackEntry.arguments?.getInt("groupId")!!,
                            onBack = {
                                navController.navigate("groups/1")
                            },
                            onEditGroup = { groupId ->
                                navController.navigate("group_details?groupId=$groupId")
                            },
                            onAddExpense = { groupId ->
                                navController.navigate("expense_details/$groupId")
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
                    "expense_details/{groupId}", arguments = listOf(
                        navArgument("groupId") {
                            type = NavType.IntType
                        },
                    )
                ) { navBackStackEntry ->
                    ExpenseDetailsPage(
                        ExpenseDetailsPageProps(
                            navBackStackEntry.arguments?.getInt("groupId")!!,
                            onSave = { navController.navigate("group/${navBackStackEntry.arguments?.getInt("groupId")!!}") },
                        )
                    )
                }
            }
        }
    }
}
