package com.example.abschlussprojekt_husewok.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

/**
 * Constants class for Firebase authentication and Firestore.
 */
class Constants {
    companion object {
        /**
         * Firebase authentication instance.
         */
        val auth = Firebase.auth

        /**
         * Firebase Firestore instance.
         */
        val firestore = Firebase.firestore
    }
}