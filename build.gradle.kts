plugins {
    kotlin("jvm") version "1.3.61"
}

repositories {
    mavenCentral()
}

val jgitVersion by extra { "5.6.0.201912101111-r" }

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("org.eclipse.jgit:org.eclipse.jgit:$jgitVersion")
}