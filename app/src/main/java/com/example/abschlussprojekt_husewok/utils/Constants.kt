package com.example.abschlussprojekt_husewok.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Constants {

    companion object {

        val auth = Firebase.auth

        val firestore = Firebase.firestore

    }

}