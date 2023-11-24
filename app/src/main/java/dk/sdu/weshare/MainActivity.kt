package dk.sdu.weshare

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.sdu.weshare.authentication.Auth
import dk.sdu.weshare.notification.NotificationThread
import dk.sdu.weshare.pages.CreateExpensePage
import dk.sdu.weshare.pages.GroupDetailsPage
import dk.sdu.weshare.pages.GroupPage
import dk.sdu.weshare.pages.GroupsPage
import dk.sdu.weshare.pages.ProfilePage
import dk.sdu.weshare.pages.RegisterPage
import dk.sdu.weshare.pages.SignInPage
import dk.sdu.weshare.ui.theme.WeShareTheme
import dk.sdu.weshare.util.ServiceBuilder
import java.io.File

class MainActivity : ComponentActivity() {
    private fun testPush() {
        // Create channel
        val channel = NotificationChannel(
            "WeShare",
            "WeShare",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = getSystemService(NotificationManager::class.java)

        manager.createNotificationChannel(channel)

        // Create notification
        val notification = NotificationCompat.Builder(this, "WeShare")
            .setContentTitle("WeShare")
            .setContentText("You have a new notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        
        // Request permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }

        // Show notification
        NotificationManagerCompat.from(this).notify(1, notification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = NotificationThread(this)
        thread.start()

        setContent {
            val navController = rememberNavController()
            ServiceBuilder.setCacheDir(File(baseContext.cacheDir, "http-cache"))
            val user = Auth.user
            val startPage = if (user == null) "signIn" else "groups"
            WeShareTheme {
                NavHost(navController = navController, startDestination = startPage) {
                    composable("signIn") {
                        SignInPage(
                            onRegister = {
                                navController.navigate("register")
                            },
                            onSignIn = {
                                navController.navigate("groups")
                            },
                        )
                    }
                    composable("register") {
                        RegisterPage(
                            onSignIn = { 
                                navController.navigate("signIn") 
                            },
                            onRegister = { 
                                navController.navigate("groups")
                            },
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
}
