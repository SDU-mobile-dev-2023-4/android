package dk.sdu.weshare.api

import android.os.Build
import dk.sdu.weshare.api.requests.AddUserToGroupRequest
import dk.sdu.weshare.api.requests.CreateGroupRequest
import dk.sdu.weshare.api.requests.LoginRequest
import dk.sdu.weshare.api.requests.RegisterRequest
import dk.sdu.weshare.api.requests.UpdateGroupRequest
import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.GroupSummary
import dk.sdu.weshare.models.Notifications
import dk.sdu.weshare.models.User
import dk.sdu.weshare.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class Api {
	companion object {
		private val retrofit = ServiceBuilder.buildService(ApiEndpoints::class.java)
		private fun <T> simpleCallback(onResult: (T?) -> Unit): Callback<T> {
			return object : Callback<T> {
				override fun onFailure(call: Call<T>, t: Throwable) {
					println("An error occurred: ${t.message}")
					onResult(null)
				}

				override fun onResponse(call: Call<T>, response: Response<T>) {
					onResult(response.body())
				}
			}
		}

		fun addExpenseToGroup(group: Group, expense: Expense, onResult: (Group?) -> Unit) {
			retrofit
				.addExpenseToGroup(group.id, expense)
				.enqueue(simpleCallback(onResult))
		}
		fun addExpenseToGroup(groupId: Int, expense: Expense, onResult: (Group?) -> Unit) {
			retrofit
				.addExpenseToGroup(groupId, expense)
				.enqueue(simpleCallback(onResult))
		}

		fun addUserToGroup(group: Group, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(group.id, AddUserToGroupRequest(user.email))
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(group: Group, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(group.id, AddUserToGroupRequest(userEmail))
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(groupId: Int, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(groupId, AddUserToGroupRequest(user.email))
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(groupId: Int, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(groupId, AddUserToGroupRequest(userEmail))
				.enqueue(simpleCallback(onResult))
		}

		fun removeUserFromGroup(group: Group, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(group.id, user.id)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(group: Group, userId: Int, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(group.id, userId)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(groupId: Int, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(groupId, user.id)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(groupId: Int, userId: Int, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(groupId, userId)
				.enqueue(simpleCallback(onResult))
		}

		fun getGroup(group: Group, onResult: (Group?) -> Unit) {

			retrofit
				.getGroup(group.id)
				.enqueue(simpleCallback(onResult))
		}
		fun getGroup(groupId: Int, onResult: (Group?) -> Unit) {
			retrofit
				.getGroup(groupId)
				.enqueue(simpleCallback(onResult))
		}

		fun updateGroup(group: Group, name: String, description: String, onResult: (Group?) -> Unit) {
			retrofit
				.updateGroup(group.id, UpdateGroupRequest(name, description))
				.enqueue(simpleCallback(onResult))
		}
		fun updateGroup(groupId: Int, name: String, description: String, onResult: (Group?) -> Unit) {
			retrofit
				.updateGroup(groupId, UpdateGroupRequest(name, description))
				.enqueue(simpleCallback(onResult))
		}

		fun deleteGroup(group: Group, onResult: (Group?) -> Unit) {
			retrofit
				.deleteGroup(group.id)
				.enqueue(simpleCallback(onResult))
		}
		fun deleteGroup(groupId: Int, onResult: (Group?) -> Unit) {
			retrofit
				.deleteGroup(groupId)
				.enqueue(simpleCallback(onResult))
		}

		fun getAllGroups(onResult: (List<GroupSummary>?) -> Unit) {
			retrofit
				.getAllGroups()
				.enqueue(simpleCallback(onResult))
		}

		fun createGroup(name: String, description: String, onResult: (Group?) -> Unit) {
			retrofit
				.createGroup(CreateGroupRequest(name, description))
				.enqueue(simpleCallback(onResult))
		}

		fun register(name: String, email: String, password: String, onResult: (User?) -> Unit) {
			retrofit
				.register(RegisterRequest(name, email, password, Build.MODEL))
				.enqueue(simpleCallback(onResult))
		}

		fun login(email: String, password: String, onResult: (User?) -> Unit) {
			retrofit
				.login(LoginRequest(email, password, Build.MODEL))
				.enqueue(simpleCallback(onResult))
		}

		fun getNotifications(onResult: (Notifications?) -> Unit) {
			retrofit
				.getNotifications()
				.enqueue(simpleCallback(onResult))
		}
	}
}
