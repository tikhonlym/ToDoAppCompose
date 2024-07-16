plugins {
    id("base-feature-plugin")
    id("hilt-convention-plugin")
}

android {
    namespace = "com.todo.featurehome"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
}