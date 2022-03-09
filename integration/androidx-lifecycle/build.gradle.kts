plugins {
    id(Deps.Plugins.Configuration.Kotlin.Android.Library)
}

dependencies {
    api(Deps.Libs.Kotlinx.Coroutines)
    api(Deps.Libs.Androidx.LifecycleViewModel)
    api(Deps.Libs.Androidx.AppCompat)
    api(project(Deps.Modules.Integration.KotlinxCoroutines))
    api(Deps.Libs.Androidx.LifecycleKtx)
}

android {
    compileSdk = Deps.compileSdkVersion

    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}