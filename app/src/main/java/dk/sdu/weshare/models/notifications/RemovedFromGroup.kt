package dk.sdu.weshare.models.notifications

import dk.sdu.weshare.models.GroupSummary
import dk.sdu.weshare.models.User

class RemovedFromGroup(rawData: Map<String, Any>) {
    val group: GroupSummary = GroupSummary(
        ((rawData["group"] as Map<String, Double>)["id"])!!.toInt(),
        ((rawData["group"] as Map<String, String>)["name"])!!,
        ((rawData["group"] as Map<String, String>)["description"])!!,
    )
    val user: User = User(
        ((rawData["removed_by"] as Map<String, Double>)["id"])!!.toInt(),
        ((rawData["removed_by"] as Map<String, String>)["name"])!!,
        ((rawData["removed_by"] as Map<String, String>)["email"])!!,
        null
    )
}

private operator fun Any?.get(s: String): Any {
    return ((this as Map<String, String>)[s]!!)
}
