package com.todo.app.tasks

import com.todo.app.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class SendApkTask @Inject constructor(
    private val api: TelegramApi,
) : DefaultTask() {

    @get: InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        val apkSizeMb = project.extensions.extraProperties["apkSizeMb"].toString()
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                runBlocking {
                    api.sendMessage(
                        "Build Success. APK size: $apkSizeMb MB",
                        token.get(),
                        chatId.get()
                    )
                    api.upload(apkFile, token.get(), chatId.get())
                }
            }
    }
}