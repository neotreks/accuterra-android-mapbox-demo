pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { // Mapbox Maven repository
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }

        maven { // AccuTerra Maven repository
            url = uri("https://distribution.accuterra.com")

            val distSiteUsername: String? = null // TODO: Set your AccuTerra username
            val distSitePassword: String? = null // TODO: Set your AccuTerra password

            credentials {
                username = distSiteUsername
                    ?: throw IllegalArgumentException("Set your AccuTerra username.")
                password = distSitePassword
                    ?:  throw IllegalArgumentException("Set your AccuTerra password.")
            }
        }
    }
}

rootProject.name = "AccuTerra Mapbox Demo"
include(":app")
 