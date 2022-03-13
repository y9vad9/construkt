plugins {
    id(Deps.Plugins.Configuration.Kotlin.Android.Library)
}

dependencies {
    implementation(Deps.Libs.Kotlinx.Coroutines)
    implementation(Deps.Libs.Androidx.AppCompat)
}

android {
    compileSdk = Deps.compileSdkVersion

    defaultConfig {
        minSdk = Deps.minSdkVersion
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-Xexplicit-api=strict", "-Xcontext-receivers", "-opt-in=kotlin.RequiresOptIn")
    }
}