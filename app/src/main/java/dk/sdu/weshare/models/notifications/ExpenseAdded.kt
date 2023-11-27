package dk.sdu.weshare.models.notifications

import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.User
import dk.sdu.weshare.models.Expense

class ExpenseAdded(rawData: Map<String, Any>) {
    val expense: Expense = Expense(
        (rawData["expense"]["payer_id"] as String).toInt(),
        rawData["expense"]["name"] as String,
        (rawData["expense"]["price"] as String).toInt()
    )
    val group: Group = Group(
        (rawData["group"]["id"] as Double).toInt(),
        rawData["group"]["name"] as String,
        rawData["group"]["description"] as String,
        null, null
    )
    val user: User = User(
        (rawData["created_by"]["id"] as Double).toInt(),
        rawData["created_by"]["name"] as String,
        rawData["created_by"]["email"] as String,
        null
    )
    val payer: User = User(
        (rawData["payer"]["id"] as Double).toInt(),
        rawData["payer"]["name"] as String,
        rawData["payer"]["email"] as String,
        null
    )
}

private operator fun Any?.get(s: String): Any {
    return (this as Map<String, Any>)[s]!!
}
    