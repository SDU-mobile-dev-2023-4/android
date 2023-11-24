package dk.sdu.weshare.notification

import dk.sdu.weshare.MainActivity
import dk.sdu.weshare.util.NotificationUtils

class NotificationThread(mainActivity: MainActivity) : Thread() {
    private val mainActivity = mainActivity

    public override fun run() {
        // Setup channel and notification manager
        try {
            NotificationUtils(mainActivity).createChannels()
        } catch (e: Exception) {
            println("Error creating channel")
        }
        
        // Run method every minute
        while (true) {
            // Check for notifications
            // If there are notifications, show them
            // Sleep for 1 minute
            sleep(10000)

            NotificationHandler(NotificationUtils(mainActivity)).handleNotifications()
        }
    }
}