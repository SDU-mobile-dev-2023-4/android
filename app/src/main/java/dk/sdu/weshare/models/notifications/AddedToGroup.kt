package dk.sdu.weshare.models.notifications

import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.User

class AddedToGroup(rawData: Map<String, Any>) {
    val group: Group = Group(
        ((rawData["group"] as Map<String, Double>)["id"])!!.toInt(),
        ((rawData["group"] as Map<String, String>)["name"])!!,
        ((rawData["group"] as Map<String, String>)["description"])!!,
        null, null
    )
    val user: User = User(
        ((rawData["added_by"] as Map<String, Double>)["id"])!!.toInt(),
        ((rawData["added_by"] as Map<String, String>)["name"])!!,
        ((rawData["added_by"] as Map<String, String>)["email"])!!,
        null
    )
}

private operator fun Any?.get(s: String): Any {
    return this as Map<String, String>[s]!!
}
