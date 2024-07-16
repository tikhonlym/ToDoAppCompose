plugins {
    id("core-convention")
    id("hilt-convention-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.todo.core"
}