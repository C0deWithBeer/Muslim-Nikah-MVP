plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.nikahtech.muslimnikah"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nikahtech.muslimnikah"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Image views
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.makeramen:roundedimageview:2.3.0")

    // OTP View
    implementation("com.github.kishansinghpanwar:FreedomOTPView:v2.0")

    // Bottom Navigation
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Glide - Image Loading library
    implementation("com.github.bumptech.glide:glide:5.0.5")

    // Shimmer Effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

}
