package dk.sdu.weshare.util

import dk.sdu.weshare.authentication.Auth
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object ServiceBuilder {
	private var cacheDir: File = File("placeholder") // Placeholder
	private lateinit var client: OkHttpClient // Late inniting, so we actually can set the cache dir in runtime
	private lateinit var retrofit: Retrofit // Late inniting, so we actually can set the cache dir in runtime

	private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.HEADERS
	}

	fun initializeClient() {
		val cache = Cache(cacheDir, 10 * 1024 * 1024)
		client = OkHttpClient.Builder().addInterceptor { chain ->
			val newRequest = chain.request().newBuilder()
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer ${Auth.user?.token}")
				.build()
			chain.proceed(newRequest)
		}
			.cache(cache)
			.addNetworkInterceptor(CacheInterceptor())
			.addInterceptor(logging)
			.build()

		retrofit = Retrofit.Builder()
			.baseUrl("https://uomi.ringhus.dk")
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
	}

	fun <T> buildService(service: Class<T>): T {
		initializeClient() // Late inniting, so we actually can set the cache dir in runtime
		return retrofit.create(service)
	}

	/**
	 * This is not thread safe
	 * Set only in the beginning of the app
	 */
	fun setCacheDir(cacheDir: File) {
		this.cacheDir = cacheDir
	}
}

class CacheInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val response: Response = chain.proceed(chain.request())
		val cacheControl = CacheControl.Builder()
			.maxAge(10, TimeUnit.MINUTES)
			.build()
		return response.newBuilder()
			.header("Cache-Control", cacheControl.toString())
			.build()
	}
}