plugins {
    id(Deps.Plugins.Configuration.Kotlin.Android.Library)
}

dependencies {
    implementation(project(Deps.Modules.Core))
    implementation(Deps.Libs.KotlinPoet.KotlinPoet)
}

android {
    compileSdk = Deps.compileSdkVersion

    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}