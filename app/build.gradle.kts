    plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.levelup.levelupgamer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.levelup.levelupgamer"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

val room_version = "2.8.3"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dependencia Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // Dependencia Hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // JbCrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Compose Material
    implementation("androidx.compose.material:material-icons-extended")
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Retrofit y kotlin serializator
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //junit
    testImplementation("junit:junit:4.13.2")
    //mock
    testImplementation("io.mockk:mockk:1.13.8")
    //coroutines test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // Para simular el servidor HTTP
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    // Para Coroutines en tests
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // 1. Para las aserciones (assertEquals, assertTrue, etc.)
    // Si usas Kotlin 1.3+ o superior, esta se incluye normalmente.
    // Si no, añádela explícitamente:
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // 2. Para la librería de Simulación de Servidor (MockWebServer)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // 3. Para OkHttp, que incluye MediaType y OkHttpClient (Retrofit lo necesita)
    // Ya la tienes, pero asegurarte de que sea testImplementation para el test:
    testImplementation("com.squareup.okhttp3:okhttp")

    // 4. Para Coroutines (Si usas runTest)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}