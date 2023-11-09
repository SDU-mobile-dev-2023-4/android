package dk.sdu.weshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshare.fakeValues.ExpenseDetailsPageProps
import dk.sdu.weshare.fakeValues.ProfilePageProps
import dk.sdu.weshare.pages.ExpenseDetailsPage
import dk.sdu.weshare.pages.GroupDetailsPage
import dk.sdu.weshare.pages.GroupPage
import dk.sdu.weshare.pages.GroupsPage
import dk.sdu.weshare.pages.ProfilePage
import dk.sdu.weshare.pages.SignInPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {
                    SignInPage(onSignIn = {
                        navController.navigate("groups")
                    })
                }
                composable("groups") {
                    GroupsPage(
                        onViewProfile = { userId: Int ->
                            navController.navigate("profile/$userId")
                        },
                        onViewGroup = { groupId: Int ->
                            navController.navigate("group/$groupId")
                        },
                        onCreateGroup = {
                            navController.navigate("group_details")
                        },
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
                                navController.navigate("groups")
                            },
                        )
                    )
                }
                composable(
                    "group/{groupId}", arguments = listOf(navArgument("groupId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    GroupPage(
                        navBackStackEntry.arguments?.getInt("groupId")!!,
                        onBack = {
                            navController.navigate("groups")
                        },
                        onEditGroup = { groupId ->
                            navController.navigate("group_details?groupId=$groupId")
                        },
                        onAddExpense = { groupId ->
                            navController.navigate("expense_details/$groupId")
                        },
                        onViewExpense = { groupId, expenseId ->
                            navController.navigate("expense_details/$groupId?expenseId=$expenseId")
                        },
                    )
                }
                composable(
                    "group_details?groupId={groupId}", arguments = listOf(navArgument("groupId") {
                        nullable = true
                    })
                ) { navBackStackEntry ->
                    GroupDetailsPage(
                        navBackStackEntry.arguments?.getString("groupId"),
                        onSave = { groupId ->
                            navController.navigate("group/$groupId")
                        },
                    )
                }
                composable(
                    "expense_details/{groupId}?expenseId={expenseId}", arguments = listOf(
                        navArgument("groupId") {
                            type = NavType.IntType
                        },
                        navArgument("expenseId") {
                            nullable = true
                        },
                    )
                ) { navBackStackEntry ->
                    ExpenseDetailsPage(
                        ExpenseDetailsPageProps(
                            navBackStackEntry.arguments?.getInt("groupId")!!,
                            navBackStackEntry.arguments?.getString("expenseId"),
                            onSave = { navController.navigate("group/${navBackStackEntry.arguments?.getInt("groupId")!!}") },
                        )
                    )
                }
            }
        }
    }
}
