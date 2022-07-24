plugins {
    id("com.android.library")
}

android {
    namespace = "org.drinkless.tdlib"
    compileSdk = 32
    ndkVersion = "24.0.8215888"

    defaultConfig {
        minSdk = 24
        targetSdk = 32

        externalNativeBuild {
            cmake {
                version = "3.18.1"
                targets += "tdjni"
                arguments += "-DCMAKE_BUILD_TYPE=MinSizeRel"
            }
        }
    }

    buildFeatures {
        buildConfig = false
    }

    buildTypes.all {
        consumerProguardFiles("proguard-rules.pro")
    }

    externalNativeBuild {
        cmake {
            path = file("jni/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.4.0")
}