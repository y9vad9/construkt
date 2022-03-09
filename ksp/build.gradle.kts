import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
    id(Deps.Plugins.Android.Library)
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(Deps.Modules.Core))
                implementation(project(Deps.Modules.Codegen))
                implementation(Deps.Libs.KSP.Api)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

android {
    compileSdk = Deps.compileSdkVersion
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}