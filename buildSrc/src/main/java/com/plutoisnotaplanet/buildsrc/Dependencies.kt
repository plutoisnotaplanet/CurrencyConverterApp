package com.plutoisnotaplanet.buildsrc

object Dependencies {

    const val APP_NAME = "com.plutoisnotaplanet.currencyconverterapp"

    object Settings {
        const val compileSdk = 33
        const val minSdk = 24
        const val targetSdk = 33
        const val buildTools = "30.0.2"
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Gradle {
        private const val version = "7.3.0"
        const val androidGradlePlugin = "com.android.tools.build:gradle:$version"
    }

    object Logging {
        private const val version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }

    object Hilt {
        private const val hiltCoreVersion = "2.44"
        private const val hiltVersion = "1.0.0"
        private const val hiltComposeVersion = "1.0.0"
        const val gradleHilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltCoreVersion"
        const val core = "com.google.dagger:hilt-android:$hiltCoreVersion"
        const val compiler = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
        const val composeNavigation = "androidx.hilt:hilt-navigation-compose:$hiltComposeVersion"
        const val androidXCompiler = "androidx.hilt:hilt-compiler:$hiltVersion"
        const val test = "com.google.dagger:hilt-android-testing:$hiltCoreVersion"
        const val kaptTest = "com.google.dagger:hilt-compiler:$hiltCoreVersion"
    }

    object Network {
        private const val okhttpVersion = "4.7.2"
        private const val retrofitVersion = "2.9.0"
        private const val gsonVersion = "2..10"
        const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Database {
        private const val roomVersion = "2.4.3"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }


    object Kotlin {
        private const val version = "1.7.20"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }


    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"

        object Compose {
            const val version = "1.3.0"
            const val compilerVersion = "1.3.2"
            private const val activityComposeVersion = "1.6.0"
            private const val accompanistVersion = "0.27.0"
            private const val navigationComposeVersion = "2.5.1"
            private const val constraintVersion = "1.0.1"

            const val materialIcons = "androidx.compose.material:material-icons-extended:$version"
            const val navigationCompose =  "androidx.navigation:navigation-compose:$navigationComposeVersion"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val compiler = "androidx.compose.compiler:compiler:$compilerVersion"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${version}"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val animation = "androidx.compose.animation:animation:${version}"
            const val activity = "androidx.activity:activity-compose:${activityComposeVersion}"
            const val constraint = "androidx.constraintlayout:constraintlayout-compose:$constraintVersion"

            const val accompanistPager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
            const val accompanistPagerIndicator = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
            const val accompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
        }

        object Lifecycle {
            private const val lifecycleVersion = "2.5.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

        }
    }
}