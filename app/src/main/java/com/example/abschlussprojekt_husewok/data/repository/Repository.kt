package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.utils.Constants.Companion.firestore
import com.google.firebase.appcheck.internal.util.Logger.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Repository(boredApi: BoredApi, jokeApi: JokeApi, userDb: UserDatabase) {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?>
        get() = _currentUser

    private val _detailedHousework = MutableStateFlow<Housework?>(null)
    val detailedHousework: StateFlow<Housework?>
        get() = _detailedHousework

    private val _activeHousework = MutableStateFlow<Housework?>(null)
    val activeHousework: StateFlow<Housework?>
        get() = _activeHousework

    private val _houseworkList = MutableStateFlow<List<Housework>>(emptyList())
    val houseworkList: StateFlow<List<Housework>>
        get() = _houseworkList

    fun updateCurrentUser(uid: String) {
        firestore.collection("user").document(uid)
            .get()
            .addOnSuccessListener { result ->
                _currentUser.value = User(
                    userId = result.id,
                    skipCoins = result.data?.get("skipCoins").toString().toLong(),
                    tasksDone = result.data?.get("tasksDone").toString().toLong(),
                    tasksSkipped = result.data?.get("tasksSkipped").toString().toLong(),
                    gamesWon = result.data?.get("gamesWon").toString().toLong(),
                    gamesLost = result.data?.get("gamesLost").toString().toLong(),
                    reward = result.data?.get("reward").toString()
                )
                Log.d(TAG, "updateCurrentUserLocal:success")
            }
            .addOnFailureListener {
                Log.w(TAG, "updateCurrentUserLocal:failure", it)
            }
    }

    fun updateCurrentUserFirestore() {
        val updatedUser = hashMapOf(
            "skipCoins" to _currentUser.value?.skipCoins,
            "tasksDone" to _currentUser.value?.tasksDone,
            "tasksSkipped" to _currentUser.value?.tasksSkipped,
            "gamesWon" to _currentUser.value?.gamesWon,
            "gamesLost" to _currentUser.value?.gamesLost,
            "reward" to _currentUser.value?.reward
        )
        firestore.collection("user").document(_currentUser.value?.userId.toString())
            .set(updatedUser)
            .addOnSuccessListener {
                Log.d(TAG, "updateCurrentUserFirebase:success")
            }
            .addOnFailureListener {
                Log.w(TAG, "updateCurrentUserFirebase:failure", it)
            }
    }

    fun changeRewardCurrentUser() {
        _currentUser.value.let {
            if (it != null) {
                _currentUser.value = User(
                    userId = it.userId,
                    skipCoins = it.skipCoins,
                    tasksDone = it.tasksDone,
                    tasksSkipped = it.tasksSkipped,
                    gamesWon = it.gamesWon,
                    gamesLost = it.gamesLost,
                    reward = if (it.reward == "Joke") "Bored" else "Joke"
                )
            }
        }
    }

    fun updateDetailedHousework(housework: Housework) {
        _detailedHousework.value = housework
    }

    private fun parseImage(image: String): Int {
        return when (image) {
            "cleanFloors" -> R.drawable.img_clean_floors
            "cleanBathroom" -> R.drawable.img_clean_bathroom
            "tidyLivingroom" -> R.drawable.img_tidy_livingroom
            "cleanOven" -> R.drawable.img_clean_oven
            "cleanRefrigerator" -> R.drawable.img_clean_refrigerator
            "emptyTrash" -> R.drawable.img_empty_trash
            "organizePaperwork" -> R.drawable.img_organize_paperwork
            "tidyBedroom" -> R.drawable.img_tidy_bedroom
            "tidyKitchen" -> R.drawable.img_tidy_kitchen
            "washClothes" -> R.drawable.img_wash_clothes
            "washDishes" -> R.drawable.img_wash_dishes
            else -> R.drawable.img_placeholder
        }
    }

    fun updateHouseworkList() {
        _currentUser.value?.let {
            firestore.collection("user").document(it.userId).collection("housework")
                .get()
                .addOnSuccessListener { result ->
                    val housework = mutableListOf<Housework>()
                    for (document in result) {
                        housework.add(Housework(
                            image = parseImage(document.data["image"].toString()),
                            title = document.data["title"].toString(),
                            task1 = document.data["task1"].toString(),
                            task2 = document.data["task2"].toString(),
                            task3 = document.data["task3"].toString(),
                            isLiked = document.data["isLiked"].toString().toBoolean(),
                            lockDurationDays = document.data["lockDurationDays"].toString().toLong(),
                            lockExpirationDate = document.data["lockExpirationDate"].toString(),
                            default = document.data["default"].toString().toBoolean()
                        ))
                    }
                    _houseworkList.value = housework
                    Log.d(TAG, "updateHouseworkListLocal:success")
                }
                .addOnFailureListener {
                    Log.w(TAG, "updateHouseworkListLocal:failure", it)
                }
        }
    }

    private fun camelCase(str: String): String {
        val words = str.split(" ")
        val builder = StringBuilder()
        for (i in 1..words.size-1) {
            val word = words[i]
            val capitalized = word.replaceFirstChar { it.uppercase() }
            builder.append(capitalized)
        }
        builder.append(words[0])
        return builder.toString()
    }
}