plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.abschlussprojekt_husewok"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.abschlussprojekt_husewok"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    // For displaying motion toasts
    implementation("com.github.Spikeysanju:MotionToast:1.4")

    // For using ConstraintLayout in Jetpack Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // For making API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // For converting JSON response to Kotlin objects
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // For parsing JSON data
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // For using Room database
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // For using Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation ("com.google.accompanist:accompanist-coil:0.7.0")

    //
    implementation("com.github.esatgozcu:Compose-Rolling-Number:1.0.5")

    implementation("com.exyte:animated-navigation-bar:1.0.0")

    implementation("com.github.popovanton0:heart-switch:1.4.0")

    implementation("com.github.SimformSolutionsPvtLtd:SSJetpackComposeSwipeableView:1.0.1")
}