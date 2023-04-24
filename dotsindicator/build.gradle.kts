plugins {
    id("com.android.library")
    kotlin("android")
}

android {

    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")
}