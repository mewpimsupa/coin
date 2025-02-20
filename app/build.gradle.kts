import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose)
    id(libs.plugins.hilt.get().pluginId)
    id("kotlin-kapt")
}

android {
    namespace = "com.pimsupa.coin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pimsupa.coin"
        minSdk = 26
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
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildFeatures {
        compose = true
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

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    implementation(libs.okhttp.logging)
    implementation(libs.coil.kt.compose)
    implementation("io.coil-kt:coil-svg:2.6.0")

    implementation(libs.androidx.material3)

    implementation(libs.androidx.material)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.androidx.work.ktx)


    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
}