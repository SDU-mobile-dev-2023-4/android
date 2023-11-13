package dk.sdu.weshare.api

import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.LoginCredentials
import dk.sdu.weshare.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

	@POST("api/groups/{groupId}/expenses")
	fun addExpenseToGroup(@Path("groupId") groupId: Int, @Body expense: Expense): Call<Expense>

	@POST("api/groups/{groupId}/users")
	fun addUserToGroup(@Path("groupId") groupId: Int, @Body user: User): Call<User>

	@DELETE("api/groups/{groupId}/users")
	fun removeUserFromGroup(@Path("groupId") groupId: Int, @Body user: User): Call<User>

	@GET("api/groups/{groupId}/users")
	fun getGroup(@Path("groupId") groupId: Int, @Body user: User): Call<User>

	@PUT("api/groups/{groupId}/users")
	fun updateGroup(@Path("groupId") groupId: Int, @Body user: User): Call<User>

	@DELETE("api/groups/{groupId}")
	fun deleteGroup(@Body group: Group): Call<Group>

	@GET("api/groups")
	fun getAllGroups(@Body group: Group): Call<Group>

	@POST("api/groups")
	fun createGroup(@Body group: Group): Call<Group>

	@POST("/api/register")
	fun register(@Body registerRequest: LoginCredentials): Call<User>

	@POST("/api/login")
	fun login(@Body loginRequest: LoginCredentials): Call<User>

}
