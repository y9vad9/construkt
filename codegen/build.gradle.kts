plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(Deps.Libs.KotlinPoet.KotlinPoet)
    api(Deps.Libs.KotlinPoet.KSP)
    api(Deps.Libs.KSP.Api)
}