plugins {
    id("com.android.library")
}

android {
    namespace = "stub"
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }

    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    annotationProcessor("dev.rikka.tools.refine:annotation-processor:3.1.1")
    compileOnly("dev.rikka.tools.refine:annotation:3.1.1")
    implementation("androidx.annotation:annotation:1.4.0")
}