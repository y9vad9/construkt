plugins {
    id(Deps.Plugins.KSP.Id)
    id(Deps.Plugins.Configuration.Kotlin.Android.App)
}

android {
    compileSdk = Deps.compileSdkVersion

    defaultConfig {
        applicationId = AppInfo.PACKAGE
        minSdk = Deps.minSdkVersion
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
    }
}

dependencies {
    implementation(Deps.Libs.Androidx.AppCompat)
    implementation(Deps.Libs.Androidx.LifecycleViewModel)
    ksp(project(Deps.Modules.KSP))
    implementation(project(Deps.Modules.Integration.Androidx))
    implementation(project(Deps.Modules.Integration.KotlinxCoroutines))
}