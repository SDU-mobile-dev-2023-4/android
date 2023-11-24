package dk.sdu.weshare.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dk.sdu.weshare.MainActivity
import dk.sdu.weshare.R

class NotificationUtils(base: Context) : ContextWrapper(base) {
    val context = this as Context

    val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createChannels()
    }

    public fun createChannels() {
        val myChannel = NotificationChannel(CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.GREEN
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        nManager.createNotificationChannel(myChannel)
    }

    public fun createNotification() {
        createNotification("UOMI", "You have a new notification")
    }

    public fun createNotification(title: String, message: String) {
        createNotification(title, message, 1)
    }

    public fun createNotification(title: String, message: String, id: Int) {
        println("Creating notification")
        // Create notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            setupPermission()
        }

        NotificationManagerCompat.from(this).notify(id, notification)
    }

    private fun setupPermission() {
        ActivityCompat.requestPermissions(
            this.context as MainActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    companion object {
        const val CHANNEL_ID = "UOMI"
        const val CHANNEL_NAME = "UOMI"
    }
}