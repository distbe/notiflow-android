plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

val compose_version by extra("1.0.1")

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "be.dist.notiflow"
        minSdk = 28
        targetSdk = 30
        versionCode = 2
        versionName = "1.0.1"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    // AndroidX
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha03")
    implementation("androidx.activity:activity-compose:1.3.1")
    // /AndroidX

    // Jetpack Compose tools
    implementation("androidx.compose.ui:ui:${compose_version}")
    implementation("androidx.compose.ui:ui-tooling:${compose_version}")
    implementation("androidx.compose.foundation:foundation:${compose_version}")
    implementation("androidx.compose.material:material:${compose_version}")
    implementation("androidx.compose.material:material-icons-core:${compose_version}")
    implementation("androidx.compose.material:material-icons-extended:${compose_version}")
    implementation("androidx.compose.runtime:runtime-livedata:${compose_version}")
    implementation("androidx.compose.runtime:runtime-rxjava2:${compose_version}")
    // /Jetpack Compose tools

    // + Compose Utils
    implementation("com.google.accompanist:accompanist-pager:0.16.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.16.1")
    implementation("com.google.accompanist:accompanist-insets:0.16.1")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    // /+ Compose Utils

    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    // /Network

    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
}