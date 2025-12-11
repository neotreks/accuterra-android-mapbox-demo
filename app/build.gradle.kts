plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.neotreks.accuterra.mapboxdemo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.neotreks.accuterra.mapboxdemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val wsBaseUrl: String? = null // TODO: Set your AccuTerra WS base URL
        val wsAuthUrl: String? = null // TODO: Set your AccuTerra WS auth URL
        val wsAuthClientId: String? = null // TODO: Set your AccuTerra WS auth client ID
        val wsAuthClientSecret: String? = null // TODO: Set your AccuTerra WS auth client secret
        val mapboxToken: String? = null // TODO: Set your Mapbox access token

        fun validateAndEncode(value: String?, errorMessage: () -> String) : String {
            if (value == null) {
                throw IllegalArgumentException(errorMessage())
            }

            return "\"${value}\""
        }

        buildConfigField("String", "WS_BASE_URL",
            validateAndEncode(wsBaseUrl) { "Set your AccuTerra WS base URL" }
        )
        buildConfigField("String", "WS_AUTH_URL",
            validateAndEncode(wsAuthUrl) { "Set your AccuTerra WS auth URL" }
        )
        buildConfigField("String", "WS_AUTH_CLIENT_ID",
            validateAndEncode(wsAuthClientId) { "Set your AccuTerra WS auth client ID" }
        )
        buildConfigField("String", "WS_AUTH_CLIENT_SECRET",
            validateAndEncode(wsAuthClientSecret) { "Set your AccuTerra WS auth client secret" }
        )
        buildConfigField("String", "MAPBOX_TOKEN",
            validateAndEncode(mapboxToken) { "Set your Mapbox access token" }
        )
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.mapbox.android.sdk)
    implementation(libs.accuterra.android.sdk)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring(libs.jdkdesugar)
}