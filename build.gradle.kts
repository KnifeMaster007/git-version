plugins {
    kotlin("jvm") version "1.3.61"
    id("com.gradle.plugin-publish") version "0.10.1"
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}


val jgitVersion by extra { "5.6.0.201912101111-r" }

version = "0.1.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("org.eclipse.jgit:org.eclipse.jgit:$jgitVersion")
}

gradlePlugin {
    plugins {
        create("gitVersionPlugin") {
            id = "com.github.knifemaster007.git-version"
            implementationClass = "com.github.knifemaster007.gradle.gitversion.GitversionPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/KnifeMaster007"
    vcsUrl = "https://github.com/KnifeMaster007/git-version"

    (plugins) {
        "gitVersionPlugin" {
            displayName = "Gradle Git version details plugin"
            description = "Plugin allows to read revision-related information from Git"
            tags = listOf("git", "version", "versioning", "buildnumber", "versionname", "versioncode")
        }
    }
}