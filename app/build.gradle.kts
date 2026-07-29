plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "app.kotlin.crudproductos"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "app.kotlin.crudproductos"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.9.0")

    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.google.firebase:firebase-auth-ktx")

    //implementation("com.google.android.gms:play-services-auth:21.2.0")
    //se utiliza para mostrar animaciones
    implementation("com.airbnb.android:lottie-compose:6.4.0")

    //notificaciones

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.compose.material3:material3:1.3.2")

    //admob
    // implementation("com.google.android.gms:play-services-ads:24.7.0")

    // Credential Manager (recomendado desde 2023)
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Convertir JSON a objetos Kotlin usando Gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // BOM de Supabase
    implementation("io.github.jan-tennert.supabase:storage-kt:2.4.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.4.1")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
    //supabase
    implementation("io.github.jan-tennert.supabase:storage-kt:2.4.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.4.1")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")

    //lottie
    implementation("com.airbnb.android:lottie-compose:6.7.1")

    //iconos
    implementation("androidx.compose.material:material-icons-extended")





}
