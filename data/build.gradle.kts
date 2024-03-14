import java.io.FileInputStream
import java.util.Locale
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.serialization)
}

val keystorePropertiesFile = file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.jtortosasen.data"
    buildFeatures.buildConfig = true

    buildTypes{
        debug {
            keystoreProperties.forEach { entry ->
                val key = (entry.key as String).uppercase(Locale.getDefault())
                val value = entry.value.toString()
                buildConfigField("String", key, value)
            }
        }
    }
}

dependencies {
    implementation(project(":domain"))

    //Ktor
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.client.mock)

    implementation(libs.kermit)
    implementation(libs.slf4j.nop)
    implementation(libs.kotlinx.coroutines.slf4j)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    //Arrow
    implementation(platform(libs.arrow.kt.stack))
    implementation(libs.arrow.kt.core)

    testImplementation(libs.junit)
}