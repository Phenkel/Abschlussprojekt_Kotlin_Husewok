package com.example.abschlussprojekt_husewok.data.repository

/*
import android.util.Log
import com.google.ai.generativelanguage.v1beta3.GenerateTextRequest
import com.google.ai.generativelanguage.v1beta3.TextPrompt
import com.google.ai.generativelanguage.v1beta3.TextServiceClient
import com.google.ai.generativelanguage.v1beta3.TextServiceSettings
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider
import com.google.api.gax.rpc.FixedHeaderProvider
 */
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GooglePalmRepository {
    private val API_KEY = "AIzaSyA-sDVvoRK2rofZu09Jj_FLRI7qkHHvU_k"

    //private var client: TextServiceClient

    private val _palmOutput = MutableStateFlow("")
    val palmOutput: StateFlow<String>
        get() = _palmOutput

    /*
    init {
        client = intialiteTextServiceClient(API_KEY)
    }

    private fun intialiteTextServiceClient(apiKey: String): TextServiceClient {
        val transportChannelProvider = InstantiatingGrpcChannelProvider.newBuilder()
            .setHeaderProvider(FixedHeaderProvider.create(hashMapOf("x-goog-api-key" to apiKey)))
            .build()
        val settings = TextServiceSettings.newBuilder()
            .setTransportChannelProvider(transportChannelProvider)
            .setCredentialsProvider(FixedCredentialsProvider.create(null))
            .build()
        val textServiceClient = TextServiceClient.create(settings)
        return textServiceClient
    }

    private fun createPrompt(textContent: String): TextPrompt {
        val textPrompt = TextPrompt.newBuilder()
            .setText(textContent)
            .build()

        return textPrompt
    }

    private fun createTextRequest(prompt: TextPrompt): GenerateTextRequest {
        return GenerateTextRequest.newBuilder()
            .setModel("models/text-bison-001")
            .setPrompt(prompt)
            .setCandidateCount(1)
            .build()
    }

    suspend fun generateText(question: String) {
        try {
            val textPrompt = createPrompt("Can you provide me with detailed instructions on how to $question")
            val textRequest = createTextRequest(textPrompt)
            val response = client.generateText(textRequest)
            val returnedText = response.candidatesList.last()
            _palmOutput.value = returnedText.output
            Log.d("GOOGLE PALM", "generateText:success")
        } catch (e: Exception) {
            _palmOutput.value = "Google PALM Error:\n" +
                    "${e.message}"
            Log.w("GOOGLE PALM", "generateText:failure", e)
        }
    }
     */
}