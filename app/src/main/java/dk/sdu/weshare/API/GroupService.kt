package dk.sdu.weshare.API

import com.fasterxml.jackson.databind.ObjectMapper
import dk.sdu.weshare.models.Group
import okhttp3.ResponseBody

class GroupService {
	private val retrofit = RetrofitClient.getClient()
	private val groupApi = retrofit.create(GroupApi::class.java)

	fun successfulGroupsResponse() {
		val groupsResponse = groupApi.getGroups()
			.execute()

		val successful = groupsResponse.isSuccessful
		val httpStatusCode = groupsResponse.code()
		val httpStatusMessage = groupsResponse.message()

		val body: List<Group>? = groupsResponse.body()
	}

	fun errorGroupsResponse() {
		val groupsResponse = groupApi.getGroups()
			.execute()

		val errorBody: ResponseBody? = groupsResponse.errorBody()

		val mapper = ObjectMapper()
		val mappedBody: ErrorResponse? = errorBody?.let { notNullErrorBody ->
			mapper.readValue(notNullErrorBody.toString(), ErrorResponse::class.java)
		}
	}

	fun headersGroupsResponse() {
		val groupsResponse = groupApi.getGroups()
			.execute()

		val headers = groupsResponse.headers()
		val customHeaderValue = headers["custom-header"]
	}
}