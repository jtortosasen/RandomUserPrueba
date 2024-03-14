plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.jtortosasen.domain"
}

dependencies {
    implementation(libs.kermit)
    implementation(libs.slf4j.nop)
    implementation(libs.kotlinx.coroutines.slf4j)

    //Arrow
    implementation(platform(libs.arrow.kt.stack))
    implementation(libs.arrow.kt.core)
}