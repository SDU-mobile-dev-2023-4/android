package dk.sdu.weshare.api.requests

import dk.sdu.weshare.api.Api
import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class RequestQueue private constructor() {
    companion object {
        // Inter thread communication channel for UI thread to add expenses to the queue
        private val queuedExpense: Channel<Triple<Int, Expense, Int>> = Channel()
        // Ensuring that requests are not blocking the UI thread
        private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
        // If the network connection is hanging, we will retry the request this many times
        private const val MAX_RETRIES = 5

        init {
            runQueue()
        }

        /**
         * This method is called from the UI thread, so we need to make sure that the
         * [sendExpense] method is called from a background thread.
         * Be careful that connections to the API is not hanging for too long.
         * Requests are run on IO threads, so they do not block the UI thread.
         */
        fun addExpenseToQueue(groupId: Int, expense: Expense) {
            coroutineScope.launch {
                queuedExpense.send(Triple(groupId, expense, 0)) // Initial retry count is 0
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun runQueue() {
            coroutineScope.launch{
                // Ran until shutdown() is called
                while (isActive) {
                    try {
                        sendExpense()
                    } catch (e: Exception) {
                        println("Failed to send expense: ${e.message}")
                    }
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private suspend fun sendExpense() {
            val (group, expense, retryCount) = queuedExpense.receive()
            Api.addExpenseToGroup(group, expense) { response ->
                println("Expense added to group with ${retryCount + 1} attempt(s)")
                if (response == null) {
                    if (retryCount < MAX_RETRIES) {
                        // sleeping before global scope, so we don't block everything
                        // amounting to 5 tries * 10 seconds = 50 seconds of trying to send the request
                        Thread.sleep(10000)
                        // If network connection hangs, this will go on forever. Even after the user has closed the app. xD
                        // Not sure how to fix this, but it's not a big issue.
                        GlobalScope.launch {
                            queuedExpense.send(Triple(group, expense, retryCount + 1))
                        }
                    } else {
                        println("Failed to add expense to group after $MAX_RETRIES retries")
                    }
                }
            }
        }

        /**
         * This class does not take up that many resources,
         * but it's still good practice to clean up after ourselves, when not needed anymore.
         */
        fun shutdown() {
            coroutineScope.cancel(CancellationException("App was shutdown"))
            queuedExpense.close()
        }
    }
}