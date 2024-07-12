plugins {
    id("base-feature-plugin")
    id("hilt-convention-plugin")
}

android {
    namespace = "com.todo.featureedit"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
}