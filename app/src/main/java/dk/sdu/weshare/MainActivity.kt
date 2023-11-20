package dk.sdu.weshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.pages.CreateExpensePage
import dk.sdu.weshare.pages.GroupDetailsPage
import dk.sdu.weshare.pages.GroupPage
import dk.sdu.weshare.pages.GroupsPage
import dk.sdu.weshare.pages.ProfilePage
import dk.sdu.weshare.pages.RegisterPage
import dk.sdu.weshare.pages.SignInPage
import dk.sdu.weshare.util.ServiceBuilder
import okhttp3.Cache
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            ServiceBuilder.setCacheDir(File(baseContext.cacheDir, "http-cache"))
            val user = Auth.user
            val startPage = if (user == null) "signIn" else "groups"
            NavHost(navController = navController, startDestination = startPage) {
                composable("signIn") {
                    SignInPage(
                        onRegister = { navController.navigate("register") },
                        onSignIn = { navController.navigate("groups") },
                    )
                }
                composable("register") {
                    RegisterPage(
                        onSignIn = { navController.navigate("signIn") },
                        onRegister = { navController.navigate("groups") },
                    )
                }
                composable("groups") {
                    GroupsPage(
                        onViewProfile = {
                            navController.navigate("profile")
                        },
                        onViewGroup = { groupId: Int ->
                            navController.navigate("group/$groupId")
                        },
                    )
                }
                composable("profile") {
                    ProfilePage(
                        onSave = { navController.popBackStack() }
                    )
                }
                composable(
                    "group/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    GroupPage(
                        navBackStackEntry.arguments?.getInt("groupId")!!,
                        onBack = {
                            navController.popBackStack()
                        },
                        onEditGroup = { groupId ->
                            navController.navigate("group/$groupId/edit")
                        },
                        onAddExpense = { groupId ->
                            navController.navigate("create_expense/$groupId")
                        },
                    )
                }
                composable(
                    "group/{groupId}/edit",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    GroupDetailsPage(
                        navBackStackEntry.arguments?.getInt("groupId")!!,
                        onSave = { navController.popBackStack() },
                        onBack = { navController.popBackStack() },
                    )
                }
                composable(
                    "create_expense/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    CreateExpensePage(
                        navBackStackEntry.arguments?.getInt("groupId")!!,
                        onSave = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
