plugins {
    alias(libs.plugins.android.application)
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.messaging)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Api Logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    // Image views
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.makeramen:roundedimageview:2.3.0")

    // OTP View
    implementation("com.github.kishansinghpanwar:FreedomOTPView:v2.0")

    // Bottom Navigation
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Glide - Image Loading library
    implementation("com.github.bumptech.glide:glide:5.0.5")
    implementation("jp.wasabeef:glide-transformations:4.3.0")

    // Shimmer Effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // country code picker
    implementation("com.hbb20:ccp:2.7.3")

    // OKHttp for network requests - ADD THIS LINE
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // Gson parser
    implementation("com.google.code.gson:gson:2.10.1")

    // StepView
    implementation("com.github.shuhart:stepview:1.5.1")

    // Image Picker & Cropper
    implementation("com.github.yalantis:ucrop:2.2.11")
    implementation("androidx.exifinterface:exifinterface:1.3.7")

    // Image Compressor
    implementation("id.zelory:compressor:3.0.1")

    // MMKV
    implementation("com.tencent:mmkv-static:1.3.1")

}
