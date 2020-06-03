package com.github.knifemaster007.gradle.gitversion

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


internal class GitversionPluginTest {
    @Test
    fun apply() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(GitversionPlugin::class.java)
        assertTrue(project.tasks.getByName("printVersionDetails") is PrintDetailsTask)
        assertTrue(project.extensions.getByName("gitVersionDetails") is GitversionExtension)
    }
}