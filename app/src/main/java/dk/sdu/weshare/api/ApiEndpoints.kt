package dk.sdu.weshare.api

import dk.sdu.weshare.api.requests.LoginRequest
import dk.sdu.weshare.api.requests.RegisterRequest
import dk.sdu.weshare.api.requests.AddUserToGroupRequest
import dk.sdu.weshare.api.requests.CreateGroupRequest
import dk.sdu.weshare.api.requests.UpdateGroupRequest
import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.Notification
import dk.sdu.weshare.models.Notifications
import dk.sdu.weshare.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEndpoints {
	@POST("api/groups/{id}/expenses")
	fun addExpenseToGroup(@Path("id") groupId: Int, @Body expense: Expense): Call<Group>

	@POST("api/groups/{id}/users")
	fun addUserToGroup(@Path("id") groupId: Int, @Body request: AddUserToGroupRequest): Call<Group>

	@DELETE("api/groups/{id}/users/{userId}")
	fun removeUserFromGroup(@Path("id") groupId: Int, @Path("userId") userId: Int): Call<Group>

	@GET("api/groups/{id}")
	fun getGroup(@Path("id") groupId: Int): Call<Group>

	@PUT("api/groups/{id}")
	fun updateGroup(@Path("id") groupId: Int, @Body request: UpdateGroupRequest): Call<Group>

	@DELETE("api/groups/{id}")
	fun deleteGroup(@Path("id") groupId: Int): Call<Group>

	@GET("api/groups")
	fun getAllGroups(): Call<List<Group>>

	@POST("api/groups")
	fun createGroup(@Body request: CreateGroupRequest): Call<Group>

	@POST("/api/register")
	fun register(@Body userRegistration: RegisterRequest): Call<User>

	@POST("/api/login")
	fun login(@Body loginCredentials: LoginRequest): Call<User>

	@Headers("Cache-Control: no-cache")
	@GET("/api/notifications")
	fun getNotifications(): Call<Notifications>
}
