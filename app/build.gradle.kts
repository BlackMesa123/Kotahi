plugins {
    id("com.android.application")
    kotlin("android")
    id("dev.rikka.tools.refine")
}

val versionMajor = 0
val versionMinor = 0
val versionPatch = 1

android {
    namespace = "io.mesalabs.kotahi"
    compileSdk = 32
    ndkVersion = "24.0.8215888"

    defaultConfig {
        applicationId = "io.mesalabs.kotahi"
        minSdk = 24
        targetSdk = 32
        versionCode = versionMajor * 100000 + versionMinor * 1000 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    }

    lint {
        disable += "AppCompatResource"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "x86", "arm64-v8a", "x86_64")
            isUniversalApk = true
        }
    }
}

configurations.all {
    exclude(group = "androidx.appcompat", module = "appcompat")
    exclude(group = "androidx.core", module = "core")
    exclude(group = "androidx.customview", module = "customview")
    exclude(group = "androidx.fragment", module = "fragment")
}

dependencies {
    // Sesl
    implementation("io.github.oneuiproject.sesl:appcompat:1.3.0")
    implementation("io.github.oneuiproject.sesl:material:1.3.0") {
        exclude(group = "io.github.oneuiproject.sesl", module = "viewpager2")
    }
    // AndroidX
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    implementation("androidx.navigation:navigation-ui:2.3.5") {
        exclude(group = "androidx.drawerlayout", module = "drawerlayout")
        exclude(group = "com.google.android.material", module = "material")
    }
    implementation("androidx.viewpager2:viewpager2:1.0.0") {
        exclude(group = "androidx.recyclerview", module = "recyclerview")
    }
    // TDLib
    implementation(project(":tdlib"))
    // Rikka
    implementation("dev.rikka.tools.refine:runtime:3.1.1")

    implementation(project(":utils:animation"))
    implementation(project(":utils:oneui"))
    implementation(project(":utils:presetimage"))
    implementation(project(":utils:stub"))
}