package com.todo.app.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            plugins.apply("com.google.dagger.hilt.android")
            dependencies.add("implementation", "com.google.dagger:hilt-android:2.48")
            dependencies.add("implementation", "androidx.hilt:hilt-navigation-compose:1.2.0")
            dependencies.add("kapt", "com.google.dagger:hilt-android-compiler:2.48")
        }
    }
}