package com.example.abschlussprojekt_husewok.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Constants object for Firebase authentication and Firestore.
 */
object Constants {
    /**
     * Firebase authentication instance.
     */
    val auth: FirebaseAuth by lazy { Firebase.auth }

    /**
     * Firebase Firestore instance.
     */
    val firestore: FirebaseFirestore by lazy { Firebase.firestore }
}