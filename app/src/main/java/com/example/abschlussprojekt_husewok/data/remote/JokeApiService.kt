package com.example.abschlussprojekt_husewok.data.remote

import com.example.abschlussprojekt_husewok.data.model.Joke
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * The base URL for the Joke API.
 */
const val JOKE_BASE_URL = "https://official-joke-api.appspot.com/"

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
    .baseUrl(JOKE_BASE_URL)
    .build()

/**
 * Retrofit service interface for the Joke API.
 */
interface JokeApiService {
    /**
     * Suspended function to get a random joke from the Joke API.
     *
     * @return A Joke object representing the random joke.
     */
    @GET("random_joke")
    suspend fun getRandomJoke(): Joke
}

/**
 * Singleton object for accessing the Joke API service.
 */
object JokeApi {
    /**
     * Lazy-initialized instance of the JokeApiService.
     */
    val retrofitService: JokeApiService by lazy { retrofit.create(JokeApiService::class.java) }
}