package com.github.knifemaster007.gradle.gitversion

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.slf4j.LoggerFactory

class GitversionPlugin: Plugin<Project> {
    val logger = LoggerFactory.getLogger("git-version-plugin")!!

    fun repoInit(project: Project): Repository? {
        return FileRepositoryBuilder()
            .setWorkTree(project.projectDir)
            .readEnvironment()
            .findGitDir()
            .build()
    }

    override fun apply(target: Project) {
        var repo = repoInit(target)
        if (repo?.workTree == null) {
            logger.warn("Git repository not found in project dir")
            return
        }
        target.extensions.create("gitVersionDetails", GitversionExtension::class.java, repo)
        target.tasks.register("printVersionDetails", PrintDetailsTask::class.java).configure {
            it.group = "git"
            it.description = "Print git version details"
        }
    }

}