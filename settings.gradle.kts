rootProject.name = "ToDoAppCompose"

include(":app")
include(":core")
include(":feature-edit")
include(":feature-home")
include(":data")
include(":domain")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
