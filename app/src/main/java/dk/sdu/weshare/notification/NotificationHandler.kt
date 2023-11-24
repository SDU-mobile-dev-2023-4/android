package dk.sdu.weshare.notification

import com.google.gson.internal.LinkedTreeMap
import dk.sdu.weshare.api.Api
import dk.sdu.weshare.models.notifications.AddedToGroup
import dk.sdu.weshare.models.notifications.ExpenseAdded
import dk.sdu.weshare.models.notifications.Reminder
import dk.sdu.weshare.models.notifications.RemovedFromGroup
import dk.sdu.weshare.util.NotificationUtils

class NotificationHandler(notificationUtils: NotificationUtils) {
    val notificationUtils = notificationUtils

    public fun handleNotifications() {
        getNotifications()
    }

    fun getNotifications() {
        println("Getting notifications")
        // Get notifications from api endpoint
        Api.getNotifications {
            if (it == null) {
                return@getNotifications
            }

            for (notification in it.notifications) {
                println(notification.id)

                // Handle notification based on notification type
                when (notification.type) {
                    "App\\Notifications\\AddedToGroup" -> {
                        println("Added to group")

                        val raw = notification.data as LinkedTreeMap<String, Any>
                        val information = AddedToGroup(raw)
                        notificationUtils.createNotification("Added to group ${information.group.name}", "You have been added to the group ${information.group.name} by ${information.user.name}", notification.id.hashCode())
                    }
                    "App\\Notifications\\RemovedFromGroup" -> {
                        println("Removed from group")

                        val raw = notification.data as LinkedTreeMap<String, Any>
                        val information = RemovedFromGroup(raw)
                        notificationUtils.createNotification("Removed from ${information.group.name}", "You have been removed from the group ${information.group.name} by ${information.user.name}", notification.id.hashCode())
                    }
                    "App\\Notifications\\ExpenseAdded" -> {
                        println("Expense added")
                        println(notification.data)

                        val raw = notification.data as LinkedTreeMap<String, Any>
                        val information = ExpenseAdded(raw)
                        notificationUtils.createNotification("Expense added", "${information.expense.name} has been added to the group ${information.group.name} by ${information.user.name} for ${information.expense.price} kr.", notification.id.hashCode())
                    }
                    "App\\Notifications\\MissingPayment" -> {
                        println("Reminder")

                        val raw = notification.data as LinkedTreeMap<String, Any>
                        val information = Reminder(raw)
                        notificationUtils.createNotification("Reminder", "${information.user.name}} reminded you to pay group ${information.group.name}", notification.id.hashCode())
                    }
                    else -> {
                        println("Unknown notification type recieved")
                    }
                }
            }
        }
    }
}