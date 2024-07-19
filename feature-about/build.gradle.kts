plugins {
    id("base-feature-plugin")
    id("hilt-convention-plugin")
}

android {
    namespace = "com.todo.featureabout"
}

dependencies {
    implementation(project(":core"))
}