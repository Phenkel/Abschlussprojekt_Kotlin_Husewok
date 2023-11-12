buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
plugins {

    // Android
    id("com.android.application") version "8.1.0" apply false

    // Kotlin
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    // Firebase
    id("com.google.gms.google-services") version "4.4.0" apply false
}