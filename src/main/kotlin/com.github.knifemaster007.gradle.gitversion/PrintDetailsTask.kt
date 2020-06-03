package com.github.knifemaster007.gradle.gitversion

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class PrintDetailsTask: DefaultTask() {
    @TaskAction
    fun printDetails() {
        extensions.getByType(GitversionExtension::class.java).let { details ->
            println("""
            Commit Time: ${details.commitTime}
            Author email: ${details.authorEmail}
            Author name: ${details.authorName}
            Short message: ${details.shortMessage}
            Full message: ${details.fullMessage}
            Short hash: ${details.shortHash}
            Full hash: ${details.fullHash}
            Tags: ${details.tags}
            Distance from tag: ${details.distanceFromTagged}
            Total commits: ${details.totalCommits}
            Is dirty: ${details.isDirty}
            """.trimIndent())
        }
    }
}