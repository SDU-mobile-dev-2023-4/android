package dk.sdu.weshare.util

import dk.sdu.weshare.authentication.Auth
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
	private val client = OkHttpClient.Builder().addInterceptor { chain ->
		val newRequest = chain.request().newBuilder()
			.addHeader("Accept", "application/json")
			.addHeader("Content-Type", "application/json")
			.addHeader("Authorization", "Bearer ${Auth.user?.token}")
			.build()
		chain.proceed(newRequest)
	}.build()

	private val retrofit = Retrofit.Builder()
		.baseUrl("https://uomi.ringhus.dk")
		.addConverterFactory(GsonConverterFactory.create())
		.client(client)
		.build()

	fun<T> buildService(service: Class<T>): T{
		return retrofit.create(service)
	}
}