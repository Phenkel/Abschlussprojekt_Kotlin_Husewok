package com.example.abschlussprojekt_husewok.data.model

/**
 * Data class representing a Joke.
 *
 * @param punchline The punchline of the joke.
 * @param setup The setup or introduction of the joke.
 * @param type The type or category of the joke.
 */
data class Joke(
    val punchline: String,
    val setup: String,
    val type: String
)