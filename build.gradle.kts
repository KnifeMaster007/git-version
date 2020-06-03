plugins {
    kotlin("jvm") version "1.3.61"
    id("com.gradle.plugin-publish") version "0.12.0"
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}


val jgitVersion by extra { "5.7.0.202003110725-r" }
val junitVersion by extra { "5.6.2" }

version = "0.1.3"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("org.eclipse.jgit:org.eclipse.jgit:$jgitVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
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