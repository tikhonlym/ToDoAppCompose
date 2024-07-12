package com.todo.app.tasks

import com.todo.app.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ValidateApkSizeTask @Inject constructor(
    private val api: TelegramApi,
) : DefaultTask() {

    @get: InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val maxSizeMb: Property<Int>

    @get:Input
    abstract val sizeValidationEnabled: Property<Boolean>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                val sizeMb = apkFile.length() / (1024 * 1024).toDouble()
                if (sizeMb > maxSizeMb.get() && sizeValidationEnabled.get()) {
                    runBlocking {
                        api.sendMessage(
                            "APK size (${sizeMb} MB) exceeds the maximum allowed size of ${maxSizeMb.get()} MB\nBuild failed.",
                            token.get(),
                            chatId.get()
                        )
                    }
                    throw GradleException("APK size ($sizeMb MB) exceeds the maximum allowed size of ${maxSizeMb.get()} MB")
                }
                project.extensions.extraProperties["apkSizeMb"] = sizeMb
            }
    }
}