plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinParcelize)
}

android {
    namespace = "com.jtortosasen.randomuserapp"
    buildFeatures.compose = true
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.jtortosasen.randomuserapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // Android
    implementation(libs.appcompat)
    implementation(libs.appcompat.resources)
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.preview)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.navigation.compose)
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    //Arrow
    implementation(platform(libs.arrow.kt.stack))
    implementation(libs.arrow.kt.core)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Logging
    implementation(libs.kermit)
    implementation(libs.slf4j.nop)
    implementation(libs.kotlinx.coroutines.slf4j)
}