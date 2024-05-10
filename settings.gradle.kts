pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url =uri("https://maven.aliyun.com/repository/google") }
        maven { url =uri("https://maven.aliyun.com/repository/public") }
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url=uri("https://maven.aliyun.com/repository/google") }
        maven { url= uri("https://maven.aliyun.com/repository/public") }
        maven { url =uri("https://www.jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "GuessSimpleDemo"
include(":app")
 