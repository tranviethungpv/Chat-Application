@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleDevtools)
    alias(libs.plugins.daggerHilt)
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.chatapp1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chatapp1"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
        aidl = true
    }
    buildToolsVersion = "34.0.0"


}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Fragment-ktx
    implementation(libs.androidx.fragment.ktx)

    // Lifecycle LiveData ktx
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Room database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // Glide
    implementation(libs.glide)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)

    // Navigation UI
    implementation(libs.androidx.navigation.ui.ktx)

    // Circle Image View
    implementation(libs.circleimageview)

    // Gson
    implementation(libs.gson)

    // Mockito
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.kotlin)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
}