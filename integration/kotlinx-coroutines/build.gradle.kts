plugins {
    id(Deps.Plugins.Configuration.Kotlin.Android.Library)
}

dependencies {
    api(project(Deps.Modules.Core))
    api(Deps.Libs.Kotlinx.Coroutines)
}

android {
    compileSdk = Deps.compileSdkVersion

    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}