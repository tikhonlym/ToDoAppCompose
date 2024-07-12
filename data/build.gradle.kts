plugins {
    id("data-convention")
    id("hilt-convention-plugin")
}

android {
    namespace = "com.todo.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
}