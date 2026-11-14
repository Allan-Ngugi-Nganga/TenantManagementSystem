// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Safe Args: generates classes for passing data between screens
    id("androidx.navigation.safeargs.kotlin") version "2.9.8" apply false
    // KSP generates Room's database code while building.
    // 2.3.x is the standalone KSP line that works with AGP 9's built-in Kotlin;
    // the older <kotlin>-<ksp> versions (2.2.x and below) do not.
    id("com.google.devtools.ksp") version "2.3.14" apply false
}
