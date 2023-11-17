package dk.sdu.weshare.api

import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
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
				.addUserToGroup(group.id, user.email)
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(group: Group, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(group.id, userEmail)
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(groupId: Int, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(groupId, user.email)
				.enqueue(simpleCallback(onResult))
		}
		fun addUserToGroup(groupId: Int, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.addUserToGroup(groupId, userEmail)
				.enqueue(simpleCallback(onResult))
		}

		fun removeUserFromGroup(group: Group, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(group.id, user.email)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(group: Group, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(group.id, userEmail)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(groupId: Int, user: User, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(groupId, user.email)
				.enqueue(simpleCallback(onResult))
		}
		fun removeUserFromGroup(groupId: Int, userEmail: String, onResult: (Group?) -> Unit) {
			retrofit
				.removeUserFromGroup(groupId, userEmail)
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

		fun updateGroup(group: Group, name: String, onResult: (Group?) -> Unit) {
			retrofit
				.updateGroup(group.id, name)
				.enqueue(simpleCallback(onResult))
		}
		fun updateGroup(groupId: Int, name: String, onResult: (Group?) -> Unit) {
			retrofit
				.updateGroup(groupId, name)
				.enqueue(simpleCallback(onResult))
		}

		fun deleteGroup(group: Group, onResult: (Error?) -> Unit) {
			retrofit
				.deleteGroup(group.id)
				.enqueue(simpleCallback(onResult))
		}
		fun deleteGroup(groupId: Int, onResult: (Error?) -> Unit) {
			retrofit
				.deleteGroup(groupId)
				.enqueue(simpleCallback(onResult))
		}

		fun getAllGroups(onResult: (List<Group>?) -> Unit) {
			retrofit
				.getAllGroups()
				.enqueue(simpleCallback(onResult))
		}

		fun createGroup(name: String, onResult: (Group?) -> Unit) {
			retrofit
				.createGroup(name)
				.enqueue(simpleCallback(onResult))
		}

		fun register(name: String, email: String, password: String, onResult: (User?) -> Unit) {
			retrofit
				.register(UserRegistration(name, email, password, android.os.Build.MODEL))
				.enqueue(simpleCallback(onResult))
		}

		fun login(email: String, password: String, onResult: (User?) -> Unit) {
			retrofit
				.login(LoginCredentials(email, password, android.os.Build.MODEL))
				.enqueue(simpleCallback(onResult))
		}
	}
}
