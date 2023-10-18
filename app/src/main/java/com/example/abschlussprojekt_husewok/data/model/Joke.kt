package com.example.abschlussprojekt_husewok.data.model

data class Joke(
    // Holds the punchline of the joke
    val punchline: String,

    // Holds the setup of the joke
    val setup: String,

    // Holds the type of the joke (e.g., "general", "programming", etc.)
    val type: String
)