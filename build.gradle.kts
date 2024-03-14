import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.jvm) apply false
}

subprojects {
    applyBaseConfig()
}

fun BaseExtension.baseConfig(){
    compileSdkVersion(34)
    defaultConfig.apply {
        targetSdk = 34
        minSdk = 27
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

fun Project.applyBaseConfig() {
    plugins.whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>().baseConfig()
            }
            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>().baseConfig()
            }
        }
    }
}