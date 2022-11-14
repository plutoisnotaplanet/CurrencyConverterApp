import com.plutoisnotaplanet.buildsrc.Dependencies

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = Dependencies.APP_NAME

    compileSdk = Dependencies.Settings.compileSdk

    defaultConfig {
        applicationId = Dependencies.APP_NAME
        minSdk = Dependencies.Settings.minSdk
        targetSdk = Dependencies.Settings.targetSdk
        versionCode = Dependencies.Settings.versionCode
        versionName = Dependencies.Settings.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.compilerVersion
    }
}

dependencies {

    //core
    implementation (Dependencies.Kotlin.stdlib)
    implementation (Dependencies.AndroidX.coreKtx)

    //compose
    implementation (Dependencies.AndroidX.Compose.runtime)
    implementation (Dependencies.AndroidX.Compose.compiler)
    implementation (Dependencies.AndroidX.Compose.foundation)
    implementation (Dependencies.AndroidX.Compose.layout)
    implementation (Dependencies.AndroidX.Compose.ui)
    implementation (Dependencies.AndroidX.Compose.material)
    implementation (Dependencies.AndroidX.Compose.animation)
    implementation (Dependencies.AndroidX.Compose.tooling)
    implementation (Dependencies.AndroidX.Compose.activity)
    implementation (Dependencies.AndroidX.Compose.navigationCompose)
    implementation (Dependencies.AndroidX.Compose.constraint)
    implementation (Dependencies.AndroidX.Compose.materialIcons)
    implementation (Dependencies.AndroidX.Compose.uiToolingPreview)
    implementation (Dependencies.AndroidX.Compose.accompanistPager)
    implementation (Dependencies.AndroidX.Compose.accompanistPagerIndicator)
    implementation (Dependencies.AndroidX.Compose.accompanistSwipeRefresh)
    debugImplementation (Dependencies.AndroidX.Compose.uiTooling)

    // network
    implementation (Dependencies.Network.retrofit)
    implementation (Dependencies.Network.gson)
    implementation (Dependencies.Network.gsonConverter)
    implementation (Dependencies.Network.okhttp)
    implementation (Dependencies.Network.logInterceptor)

    //room
    implementation (Dependencies.Database.runtime)
    implementation (Dependencies.Database.ktx)
    kapt (Dependencies.Database.compiler)

    //lifecycle
    implementation (Dependencies.AndroidX.Lifecycle.runtime)
    implementation (Dependencies.AndroidX.Lifecycle.viewModel)

    // coroutines
    implementation (Dependencies.Coroutines.coroutinesAndroid)
    testImplementation (Dependencies.Coroutines.test)

    // hilt
    implementation (Dependencies.Hilt.core)
    implementation (Dependencies.Hilt.composeNavigation)
    kapt (Dependencies.Hilt.compiler)
    kapt (Dependencies.Hilt.androidXCompiler)
    kaptAndroidTest (Dependencies.Hilt.kaptTest)
    androidTestImplementation (Dependencies.Hilt.test)

    //logging
    implementation (Dependencies.Logging.timber)

    androidTestImplementation (Dependencies.AndroidX.Test.core)
    androidTestImplementation (Dependencies.AndroidX.Test.Ext.junit)
    androidTestImplementation (Dependencies.AndroidX.Test.rules)
}