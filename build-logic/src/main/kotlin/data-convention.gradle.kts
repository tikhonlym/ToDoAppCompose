import gradle.kotlin.dsl.accessors._543a519d4f924bfc205e6a94013256c0.androidTestImplementation
import gradle.kotlin.dsl.accessors._543a519d4f924bfc205e6a94013256c0.testImplementation
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    baseAndroidConfig()
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    // Retrofit okhttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Room
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.mockito.android)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}