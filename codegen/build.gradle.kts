plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(Deps.Libs.KotlinPoet.KotlinPoet)
    api(Deps.Libs.KotlinPoet.KSP)
    api(Deps.Libs.KSP.Api)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xcontext-receivers",
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview"
        )
    }
}