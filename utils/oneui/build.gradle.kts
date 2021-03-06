plugins {
    id("com.android.library")
    id("dev.rikka.tools.refine")
}

android {
    namespace = "io.mesalabs.oneui"
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }

    buildFeatures {
        buildConfig = false
        viewBinding = true
    }
}

configurations.all {
    exclude(group = "androidx.appcompat", module = "appcompat")
    exclude(group = "androidx.core", module = "core")
}

dependencies {
    // Sesl
    implementation("io.github.oneuiproject.sesl:appcompat:1.3.0")
    implementation("io.github.oneuiproject.sesl:coordinatorlayout:1.0.0")
    implementation("io.github.oneuiproject.sesl:material:1.3.0") {
        exclude(group = "io.github.oneuiproject.sesl", module = "viewpager2")
    }
    // AndroidX
    implementation("androidx.annotation:annotation:1.4.0")
    // Rikka
    implementation("dev.rikka.tools.refine:runtime:3.1.1")

    compileOnly(project(":utils:stub"))
}