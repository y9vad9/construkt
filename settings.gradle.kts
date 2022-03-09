pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    plugins {
        id("com.google.devtools.ksp") version "1.6.20-RC-1.0.4"
        id("org.jetbrains.kotlin.android") version "1.6.20-RC"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.kotlingang.fun")
        maven("https://maven.y9vad9.com")
    }
}

rootProject.name = "construct"

includeBuild("build-logic/dependencies")
includeBuild("build-logic/configuration")
//includeBuild("buildUtils/library-deploy")

include(":core", ":codegen", ":ksp", ":example", ":integration:kotlinx-coroutines", ":integration:androidx-lifecycle")
