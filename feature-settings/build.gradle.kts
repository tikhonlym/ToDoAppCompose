plugins {
    id("base-feature-plugin")
    id("hilt-convention-plugin")
}

android {
    namespace = "com.todo.featuresettings"
}

dependencies {
    implementation(project(":core"))
}