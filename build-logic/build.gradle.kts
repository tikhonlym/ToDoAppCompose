plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("tg-plugin") {
        id = "tg-plugin"
        implementationClass = "com.todo.app.plugins.TgPlugin"
    }
    plugins.register("hilt-convention-plugin") {
        id = "hilt-convention-plugin"
        implementationClass = "com.todo.app.plugins.HiltConventionPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.ktor.client)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
