plugins {
    alias(libs.plugins.android.application)
    // AGP 9 has built-in Kotlin support, so the Kotlin Android plugin is NOT applied.
    // Data Binding generates its binding classes here without kapt.
    // Safe Args: must come BELOW the android.application line.
    id("androidx.navigation.safeargs.kotlin")
    // KSP: generates Room's boilerplate at compile time
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.tenantmanagementsystemgroupa"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.tenantmanagementsystemgroupa"
        minSdk = 24
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
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.8")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.8")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    // MaterialToolbar, NavigationView and the Material themes live here
    implementation(libs.material)
    // Architecture Components: ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    // Gives Fragments "by activityViewModels()" (used in Stage 4)
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    // Room: the local database library
    implementation("androidx.room:room-runtime:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    // Generates the Room code (ksp, not implementation)
    ksp("androidx.room:room-compiler:2.7.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
