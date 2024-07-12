package com.todo.app.plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.todo.app.api.TelegramApi
import com.todo.app.tasks.AnalyzeApkTask
import com.todo.app.tasks.SendApkTask
import com.todo.app.tasks.ValidateApkSizeTask
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create


interface TgExtension {
    val token: Property<String>
    val chatId: Property<String>
    val maxSizeMb: Property<Int>
    val sizeValidationEnabled: Property<Boolean>
    val apkAnalyzeEnabled: Property<Boolean>
}

class TgPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android plugin required.")

        val ext = project.extensions.create("tgReportPlugin", TgExtension::class)

        val api = TelegramApi(HttpClient(OkHttp))

        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)

            val validateTaskProvider = project.tasks.register(
                "validateApkSizeFor${variant.name}",
                ValidateApkSizeTask::class.java,
                api
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(ext.token)
                    chatId.set(ext.chatId)
                    sizeValidationEnabled.set(ext.sizeValidationEnabled)
                    maxSizeMb.set(ext.maxSizeMb)
                }
            }

            val reportBuildProvider =
                project.tasks.register(
                    "reportBuildFor${variant.name}",
                    SendApkTask::class.java,
                    api
                )
                    .apply {
                        configure {
                            apkDir.set(artifacts)
                            token.set(ext.token)
                            chatId.set(ext.chatId)
                            dependsOn(validateTaskProvider)
                        }
                    }

            val analyzeApkTaskProvider = project.tasks.register(
                "analyzeApkFor${variant.name}",
                AnalyzeApkTask::class.java,
                api
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(ext.token)
                    chatId.set(ext.chatId)
                    dependsOn(reportBuildProvider)
                    mustRunAfter(reportBuildProvider)
                }
            }

            project.tasks.register("fullApkTelegramReportFor${variant.name}") {
                dependsOn(validateTaskProvider)
                dependsOn(reportBuildProvider)
                if (ext.apkAnalyzeEnabled.get()) {
                    dependsOn(analyzeApkTaskProvider)
                }
            }
        }
    }
}