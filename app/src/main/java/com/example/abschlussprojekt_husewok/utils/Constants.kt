package com.example.abschlussprojekt_husewok.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Constants object for Firebase authentication and Firestore.
 */
object Constants {
    /**
     * Firebase authentication instance.
     */
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    /**
     * Firebase Firestore instance.
     */
    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}