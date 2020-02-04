package com.github.knifemaster007.gradle.gitversion

import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevSort
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.slf4j.LoggerFactory
import java.time.Instant

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
            repo = null
            logger.warn("Git repository not found in project dir")
            return
        }
        val extension = target.extensions.create("gitVersionDetails", GitversionExtension::class.java, repo)

        logger.warn("""
            Commit Time: ${extension.commitTime}
            Author email: ${extension.authorEmail}
            Author name: ${extension.authorName}
            Short message: ${extension.shortMessage}
            Full message: ${extension.fullMessage}
            Short hash: ${extension.shortHash}
            Full hash: ${extension.fullHash}
            Tags: ${extension.tags}
            Distance from tag: ${extension.distanceFromTagged}
            Total commits: ${extension.totalCommits}
            Is dirty: ${extension.isDirty}
            """.trimIndent())

    }

}