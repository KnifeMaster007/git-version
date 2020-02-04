package com.github.knifemaster007.gradle.gitversion

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevSort
import org.eclipse.jgit.revwalk.RevWalk
import java.time.Instant

open class GitversionExtension(private val repo: Repository?) {
    private class TreeMetaInfo {
        var distanceFromTagged: Int? = null
        var totalCommits: Int = 0
        var nearestTags: List<Ref>? = null
    }

    private val headRef: Ref? by lazy {
        repo?.findRef(Constants.HEAD)
    }

    private val walker: RevWalk? by lazy {
        repo?.let { RevWalk(it) }
    }

    private val headCommit: RevCommit? by lazy {
        headRef?.let { walker?.parseCommit(it.objectId) }
    }

    private val repoTags: List<Ref>? by lazy {
        repo?.refDatabase?.getRefsByPrefix(Constants.R_TAGS)
    }

    private val treeMeta: TreeMetaInfo? by lazy {
        walker?.let { wk ->
            val meta = TreeMetaInfo()
            wk.reset()
            wk.sort(RevSort.COMMIT_TIME_DESC)
            wk.markStart(headCommit)
            wk.mapIndexed { index, revCommit ->
                repoTags?.let { tags ->
                    if (meta.distanceFromTagged == null) {
                        val commitTags = tags.filter { it.objectId == revCommit.toObjectId() }
                        if (commitTags.isNotEmpty()) {
                            meta.distanceFromTagged = index
                            meta.nearestTags = commitTags
                        }
                    }

                }
                meta.totalCommits += 1
            }
            meta
        }
    }

    val tags: List<String>? by lazy {
        treeMeta?.nearestTags?.map {
            it.name.drop(Constants.R_TAGS.length)
        }
    }

    val distanceFromTagged: Int? by lazy {
        treeMeta?.distanceFromTagged
    }

    val totalCommits: Int? by lazy {
        treeMeta?.totalCommits
    }

    val shortMessage: String? by lazy {
        headCommit?.shortMessage
    }

    val fullMessage: String? by lazy {
        headCommit?.fullMessage
    }

    val commitTime: Instant? by lazy {
        headCommit?.commitTime?.let { Instant.ofEpochSecond(it.toLong()) }
    }

    val authorName: String? by lazy {
        headCommit?.authorIdent?.name
    }

    val authorEmail: String? by lazy {
        headCommit?.authorIdent?.emailAddress
    }

    val fullHash: String? by lazy {
        headCommit?.id?.name
    }

    val shortHash: String? by lazy {
        headCommit?.id?.let { repo?.newObjectReader()?.abbreviate(it) }?.name()
    }

    val isDirty: Boolean? by lazy {
        repo?.let { !Git(it).status().call().isClean }
    }
}