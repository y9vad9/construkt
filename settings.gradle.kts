pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
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

include(":core", ":codegen")
