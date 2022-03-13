import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Mpp)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(Deps.Modules.Codegen))
                implementation(Deps.Libs.KSP.Api)
                implementation(Deps.Libs.KotlinPoet.KotlinPoet)
                implementation(Deps.Libs.KotlinPoet.KSP)
                implementation(project(Deps.Modules.Annotation))
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers", "-opt-in=kotlin.RequiresOptIn")
    }
}