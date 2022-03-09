plugins {
    id(Deps.Plugins.Configuration.Kotlin.Android.Library)
}

dependencies {
    implementation(Deps.Libs.Kotlinx.Coroutines)
}

android {
    compileSdk = Deps.compileSdkVersion

    kotlinOptions {
        freeCompilerArgs = listOf("-Xexplicit-api=strict")
    }
}