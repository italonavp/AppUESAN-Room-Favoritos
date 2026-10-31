import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")


    id("com.google.devtools.ksp")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { input ->
            load(input)
        }
    }
}

val apiBaseUrl = localProperties.getProperty("API_BASE_URL", "https://v3.football.api-sports.io/")
val apiKey = localProperties.getProperty("API_KEY", "")

android {
    namespace = "com.example.appuesan"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.appuesan"
        minSdk = 34
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    //Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.10.1")
    //Coil Compose - mostrar imagenes (url)
    implementation("io.coil-kt:coil-compose:2.7.0")
    //Material Icons
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.19.0"))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)


    //Supabase Storage
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.storage.kt)
    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room - Persistencia local
    implementation("androidx.room:room-runtime:2.8.5")
    implementation("androidx.room:room-ktx:2.8.5")
    ksp("androidx.room:room-compiler:2.8.5")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}