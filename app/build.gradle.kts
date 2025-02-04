plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //alias(libs.plugins.kotlin.kapt)
    kotlin("kapt")
}

android {
    namespace = "com.hrv.swordchallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hrv.swordchallenge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Room dependencies
    implementation(libs.androidx.room.runtime)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.insert.koin.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.retrofit) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.converter.gson) {
        exclude(group = "com.intellij", module = "annotations")
    }

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.accompanist.swiperefresh)

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockito.core.v3112)
    testImplementation(libs.mockito.kotlin.v320)
    testImplementation(libs.kotlinx.coroutines.test.v160)
    testImplementation(libs.google.truth)

    testImplementation(libs.google.truth)
}
