package dk.sdu.weshare.api

import dk.sdu.weshare.models.Expense
import dk.sdu.weshare.models.Group
import dk.sdu.weshare.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
	@POST("api/groups/{id}/expenses")
	fun addExpenseToGroup(@Path("id") groupId: Int, @Body expense: Expense): Call<Group>

	@POST("api/groups/{id}/users")
	fun addUserToGroup(@Path("id") groupId: Int, @Query("email") userEmail: String): Call<Group>

	@DELETE("api/groups/{id}/users")
	fun removeUserFromGroup(@Path("id") groupId: Int, @Query("email") userEmail: String): Call<Group>

	@GET("api/groups/{id}")
	fun getGroup(@Path("id") groupId: Int): Call<Group>

	@PUT("api/groups/{id}")
	fun updateGroup(@Path("id") groupId: Int, @Query("name") name: String): Call<Group>

	@DELETE("api/groups/{id}")
	fun deleteGroup(@Path("id") groupId: Int): Call<Error>

	@GET("api/groups")
	fun getAllGroups(): Call<List<Group>>

	@POST("api/groups")
	fun createGroup(@Query("name") name: String): Call<Group>

	@POST("/api/register")
	fun register(@Query("name") name: String, @Query("email") email: String, @Query("password") password: String, @Query("device_name") deviceName: String): Call<User>

	@POST("/api/login")
	fun login(@Query("email") email: String, @Query("password") password: String, @Query("device_name") deviceName: String): Call<User>
}
