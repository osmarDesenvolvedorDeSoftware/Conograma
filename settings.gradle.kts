pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io") // Se estiver usando dependências do JitPack
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Essa configuração ativa o erro se os repositórios estiverem no build.gradle.kts
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Se necessário
    }
}

rootProject.name = "MeuCronograma"
include(":app")
