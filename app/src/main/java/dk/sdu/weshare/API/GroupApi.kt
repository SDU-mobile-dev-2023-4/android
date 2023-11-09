package dk.sdu.weshare.API

import dk.sdu.weshare.models.Group
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GroupApi {
	@GET("groups")
	fun getGroups(): Call<List<Group>>

	// Works exactly the same, as above
	@HTTP(method = "GET", path = "groups")
	fun httpGroups(): Call<List<Group>>

	@POST("groups")
	fun postGroups(): Call<List<Group>>

	@PUT("groups")
	fun putGroups(): Call<List<Group>>

	@PATCH("groups")
	fun patchGroups(): Call<List<Group>>

	@DELETE("groups")
	fun deleteGroups(): Call<List<Group>>

	@DELETE("groups")
	fun failingDeleteGroups(@Body group: Group): Call<Group>

	@HTTP(method = "DELETE", path = "groups", hasBody = true)
	fun workingDeleteGroups(@Body group: Group): Call<Group>

	@OPTIONS("groups")
	fun optionsGroups(): Call<List<Group>>

	// Must be Void
	@HEAD("groups")
	fun headGroups(): Call<Void>

	// Replaces the BASE_URL
	@GET("http://localhost:8090/v3/groups")
	fun getGroupsV3(): Call<List<Group>>

	@GET
	fun getGroupsDynamic(@Url url: String): Call<List<Group>>

	@GET("groups/{groupId}")
	fun getGroupById(@Path("groupId") groupId: Int): Call<Group>

	@GET("groups?sort_order=asc")
	fun getGroupsStaticQueryParam(): Call<List<Group>>

	@GET("groups")
	fun getGroupsDynamicQueryParam(@Query("sort_order") order: String): Call<List<Group>>

	@GET("groups")
	fun getGroupsDynamicQueryMap(@QueryMap parameters: Map<String, String>): Call<List<Group>>

	@Headers("Group-Agent: codersee-application")
	@GET("groups")
	fun getGroupsSingleStaticHeader(): Call<List<Group>>

	@Headers(
		value = [
			"Group-Agent: codersee-application",
			"Custom-Header: my-custom-header"
		]
	)
	@GET("groups")
	fun getGroupsMultipleStaticHeaders(): Call<List<Group>>

	@GET("groups")
	fun getGroupsDynamicHeader(@Header("Authorization") token: String): Call<List<Group>>

	@GET("groups")
	fun getGroupsHeaderMap(@HeaderMap headers: Map<String, String>): Call<List<Group>>

	@POST("groups")
	fun postGroupsWithPayload(@Body group: Group): Call<Group>

	@FormUrlEncoded
	@POST("groups")
	fun postGroupsFormUrlEncoded(@Field("field_one") fieldOne: String): Call<Group>

	@Multipart
	@POST("groups")
	fun postGroupsMultipart(@Part("something") partOne: RequestBody): Call<Group>
}
