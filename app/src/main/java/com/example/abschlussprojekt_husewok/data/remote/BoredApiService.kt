package com.example.abschlussprojekt_husewok.data.remote

import com.example.abschlussprojekt_husewok.data.model.Bored
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Define the base URL for the API
const val BORED_BASE_URL = "https://www.boredapi.com/api/"

// Create an instance of OkHttpClient with an interceptor
private val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder().build()
        chain.proceed(newRequest)
    }
    .build()

// Create an instance of Moshi with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Create an instance of Retrofit with the OkHttpClient and MoshiConverterFactory
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BORED_BASE_URL)
    .build()

// Define the interface for the Bored API service
interface BoredApiService {
    @GET("activity")
    suspend fun getRandomJoke(): Bored
}

// Create a singleton object for the Bored API
object BoredApi {
    val retrofitService: BoredApiService by lazy { retrofit.create(BoredApiService::class.java) }
}