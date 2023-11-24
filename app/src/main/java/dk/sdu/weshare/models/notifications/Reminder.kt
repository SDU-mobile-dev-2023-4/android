package dk.sdu.weshare.models.notifications

class Reminder(rawData: Map<String, Any>) {
    val group: String = rawData["group"] as String
}