plugins {
    id("android-app-convention")
    id("hilt-convention-plugin")
    id("tg-plugin")
}

tgReportPlugin {
    token.set(providers.gradleProperty("TG_TOKEN"))
    chatId.set(providers.gradleProperty("TG_CHAT"))
    maxSizeMb.set(providers.gradleProperty("APK_MAX_SIZE").map { it.toInt() })
    sizeValidationEnabled.set(providers.gradleProperty("SIZE_VALIDATION_ENABLED").map { it.toBoolean() })
    apkAnalyzeEnabled.set(providers.gradleProperty("APK_ANALYZE_ENABLED").map { it.toBoolean() })
}

android {
    namespace = "com.todo.app.todoappcompose"
    defaultConfig {
        applicationId = "com.todo.app.todoappcompose"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":feature-edit"))
    implementation(project(":feature-about"))
    implementation(project(":feature-home"))
    implementation(project(":feature-settings"))
    implementation(project(":core"))
    implementation(project(":data"))
}
