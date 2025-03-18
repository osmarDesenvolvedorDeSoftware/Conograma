plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Garante que o JitPack esteja aqui também
    }
}
