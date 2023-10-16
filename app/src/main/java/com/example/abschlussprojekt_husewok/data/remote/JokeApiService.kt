package com.example.abschlussprojekt_husewok.data.remote

import com.example.abschlussprojekt_husewok.data.model.Joke
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Define the base URL for the joke API
const val JOKE_BASE_URL = "https://official-joke-api.appspot.com/"

// Create an OkHttpClient with an interceptor
private val client = OkHttpClient.Builder().addInterceptor { chain ->
    val newRequest: Request = chain.request().newBuilder().build()
    chain.proceed(newRequest)
}.build()

// Create a Moshi instance with KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Create a Retrofit instance with the OkHttpClient, MoshiConverterFactory, and the base URL
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(JOKE_BASE_URL)
    .build()

// Define an interface for the joke API service
interface JokeApiService {
    @GET("random_joke")
    suspend fun getRandomJoke(): Joke
}

// Create a singleton object for the JokeApi
object JokeApi {
    val retrofitService: JokeApiService by lazy { retrofit.create(JokeApiService::class.java) }
}