pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
        maven { url = uri("https://maven.google.com") }
        mavenLocal()
    }
}

rootProject.name = "Abschlussprojekt_Husewok"
include(":app")
 