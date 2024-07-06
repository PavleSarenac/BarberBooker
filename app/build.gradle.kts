plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "rs.ac.bg.etf.barberbooker"
    compileSdk = 34

    defaultConfig {
        applicationId = "rs.ac.bg.etf.barberbooker"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // VIEWMODEL
    // https://maven.google.com/web/index.html?q=viewmode#androidx.lifecycle:lifecycle-viewmodel:2.8.3
    implementation(libs.androidx.lifecycle.viewmodel)

    // NAVIGATION
    // https://maven.google.com/web/index.html?q=androidx.navigation#androidx.navigation:navigation-compose:2.8.0-beta04
    implementation(libs.androidx.navigation.compose)

    // ROOM
    // https://maven.google.com/web/index.html?#androidx.room:room-ktx:2.7.0-alpha04
    implementation(libs.androidx.room.ktx)
    // https://maven.google.com/web/index.html?#androidx.room:room-runtime:2.7.0-alpha04
    implementation(libs.androidx.room.runtime)
    // https://maven.google.com/web/index.html?#androidx.room:room-compiler:2.7.0-alpha04
    annotationProcessor(libs.androidx.room.compiler)
    // https://maven.google.com/web/index.html?#androidx.room:room-compiler:2.7.0-alpha04
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    // HILT
    // https://developer.android.com/training/dependency-injection/hilt-android
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ICONS
    // https://maven.google.com/web/index.html?q=icons#androidx.compose.material:material-icons-extended:1.7.0-beta04
    implementation(libs.androidx.material.icons.extended)
}

kapt {
    correctErrorTypes = true
}