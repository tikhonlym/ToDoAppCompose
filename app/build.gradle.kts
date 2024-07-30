plugins {
    id("android-app-convention")
    id("hilt-convention-plugin")
    id("tg-plugin")
}

tgReportPlugin {
    token.set(providers.environmentVariable("TG_TOKEN"))
    chatId.set(providers.environmentVariable("TG_CHAT"))

    maxSizeMb.set(20)
    sizeValidationEnabled.set(true)
    apkAnalyzeEnabled.set(true)
}

android {
    namespace = "com.todo.app.todoappcompose"
    defaultConfig {
        applicationId = "com.todo.app.todoappcompose"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
