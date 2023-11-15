package com.example.abschlussprojekt_husewok.data.remote

import com.example.abschlussprojekt_husewok.data.model.Bored
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * The base URL for the Bored API.
 */
const val BORED_BASE_URL = "https://www.boredapi.com/api/"

/**
 * OkHttpClient instance with an interceptor.
 */
private val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder().build()
        chain.proceed(newRequest)
    }
    .build()

/**
 * Moshi instance with KotlinJsonAdapterFactory.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Retrofit instance with OkHttpClient and MoshiConverterFactory.
 */
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BORED_BASE_URL)
    .build()

/**
 * Retrofit service interface for the Bored API.
 */
interface BoredApiService {
    /**
     * Suspended function to get a random joke from the Bored API.
     *
     * @return A Bored object representing the random joke.
     */
    @GET("activity")
    suspend fun getRandomBored(): Bored
}

/**
 * Singleton object for accessing the Bored API service.
 */
object BoredApi {
    /**
     * Lazy-initialized instance of the BoredApiService.
     */
    val retrofitService: BoredApiService by lazy { retrofit.create(BoredApiService::class.java) }
}