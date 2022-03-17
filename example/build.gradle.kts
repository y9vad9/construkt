plugins {
    id(Deps.Plugins.KSP.Id)
    id(Deps.Plugins.Configuration.Kotlin.Android.App)
}

android {
    compileSdk = Deps.compileSdkVersion

    defaultConfig {
        applicationId = "${AppInfo.PACKAGE}.example.app"
        minSdk = Deps.minSdkVersion
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
        getByName("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xcontext-receivers")
    }
}

dependencies {
    implementation(Deps.Libs.Androidx.AppCompat)
    implementation(Deps.Libs.Androidx.LifecycleViewModel)
    implementation(Deps.Libs.Androidx.LifecycleKtx)
    implementation(Deps.Libs.Kotlinx.Coroutines)
    implementation(project(Deps.Modules.Core))
    ksp(project(Deps.Modules.KSP))
    implementation(project(Deps.Modules.Annotation))
}