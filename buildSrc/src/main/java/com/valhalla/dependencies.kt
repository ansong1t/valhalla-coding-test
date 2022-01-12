package com.valhalla

object Versions {
    const val ktlint = "0.29.0"
}

object Android {
    // Manifest version information!
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 6 // bump for dogfood builds, public betas, etc.

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 10 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdkVersion = 31
    const val targetSdkVersion = 31
    const val minSdkVersion = 23
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.0"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"
    const val threeTenBp = "org.threeten:threetenbp:1.4.4"
    const val threeTenBpNoTzdb = "$threeTenBp:no-tzdb"
    const val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:1.2.4"
    const val chucker = "com.github.chuckerteam.chucker:library:3.2.0"
    const val chuckerNoop = "com.github.chuckerteam.chucker:library-no-op:3.2.0"
    const val mockito = "org.mockito:mockito-inline:2.25.0"
    const val store = "com.dropbox.mobile.store:store4:4.0.2-KT15"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val imagePicker = "com.github.dhaval2404:imagepicker:1.7.3"
    const val runtimePermission = "com.github.florent37:runtime-permission-kotlin:1.1.2"
    const val geoFirestore = "com.github.imperiumlabs:GeoFirestore-Android:v1.5.0"
    const val grpc = "io.grpc:grpc-okhttp:1.32.2"
    const val barcodeScanner = "me.dm7.barcodescanner:zxing:1.9.13"

    object Kotlin {
        private const val version = "1.5.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val viewpager = "androidx.viewpager2:viewpager2:1.0.0"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-rc01"
        // Security Crypto
        const val securityCrypto = "androidx.security:security-crypto:1.0.0"

        object Navigation {
            private const val version = "2.4.0-alpha06"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.4.0-alpha01"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
            const val fragmentTesting = "androidx.fragment:fragment-testing:$version"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Paging {
            private const val version = "3.0.0"
            const val common = "androidx.paging:paging-common:$version"
            const val runtime = "androidx.paging:paging-runtime:$version"
        }

        object Room {
            private const val version = "2.3.0"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        object Work {
            private const val version = "2.5.0"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }
    }

    object Anko {
        private const val version = "0.10.8"
        const val common = "org.jetbrains.anko:anko-commons:$version"
    }

    object Coil {
        private const val version = "1.3.0"
        const val coil = "io.coil-kt:coil:$version"
    }

    object Epoxy {
        private const val version = "4.6.2"
        const val epoxy = "com.airbnb.android:epoxy:$version"
        const val paging = "com.airbnb.android:epoxy-paging3:$version"
        const val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        const val processor = "com.airbnb.android:epoxy-processor:$version"
    }

    object Glide {
        private const val version = "4.11.0"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val glide = "com.github.bumptech.glide:glide:$version"
    }

    object Google {
        const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.7.1"
        const val crashlytics = "com.google.firebase:firebase-crashlytics"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
        const val firebaseBom = "com.google.firebase:firebase-bom:28.3.1"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging"
        const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
        const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
        const val geoFire = "com.firebase:geofire-android-common:3.1.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val truth = "com.google.truth:truth:0.42"
        const val gmsGoogleServices = "com.google.gms:google-services:4.3.8"

        object Hilt {
            private const val version = "2.35"
            const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val hiltAndroid = "com.google.dagger:hilt-android:$version"
            const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$version"
        }

        object Dagger {
            private const val version = "2.29.1"
            const val dagger = "com.google.dagger:dagger:$version"
            const val compiler = "com.google.dagger:dagger-compiler:$version"
        }

        object Maps {
            // Google Maps
            private const val playServicesMapsVersion = "17.0.0"
            private const val playServicesLocationVersion = "16.0.0"
            private const val playServicesPlacesVersion = "16.0.0"
            private const val playMapsUtilVersion = "2.2.0"
            private const val placesVersion = "2.3.0"

            // Google Maps
            val playServicesMaps = "com.google.android.gms:play-services-maps:$playServicesMapsVersion"
            val playServicesPlaces = "com.google.android.gms:play-services-places:$playServicesPlacesVersion"
            val playServicesLocation = "com.google.android.gms:play-services-location:$playServicesLocationVersion"
            val playMapsUtil = "com.google.maps.android:android-maps-utils:$playMapsUtilVersion"
            val places = "com.google.android.libraries.places:places:$placesVersion"

            const val mapsPlatformGradle =
                "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:1.3.0"
        }
        val playServicesAuth = "com.google.android.gms:play-services-auth:17.0.0"
    }

    object AssistedInject {
        private const val version = "0.6.0"
        const val annotationDagger2 = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processorDagger2 = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }

    object Insetter {
        private const val version = "0.6.0"
        const val core = "dev.chrisbanes.insetter:insetter:$version"
    }

    object Junit5 {
        private const val version = "1.0.0"
        const val core = "de.mannodermaus.junit5:android-test-core:$version"
        const val runner = "de.mannodermaus.junit5:android-test-runner:$version"
        const val gradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0"
    }

    const val jacoco = "org.jacoco:org.jacoco.core:0.8.1"

    object Moshi {
        private const val version = "1.12.0"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Spek {
        private const val spekVersion = "2.0.10"
        const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:$spekVersion"
        const val junit = "org.spekframework.spek2:spek-runner-junit5:$spekVersion"
    }

    object SQLCipher {
        private const val sqlChipherVersion = "4.4.3"
        private const val sqliteVersion = "2.1.0"
        const val sqlcipher = "net.zetetic:android-database-sqlcipher:$sqlChipherVersion"
        const val sqlite = "androidx.sqlite:sqlite:$sqliteVersion"
    }

    object Roomigrant {
        private const val version = "0.3.4"
        const val library = "com.github.MatrixDev.Roomigrant:RoomigrantLib:$version"
        const val compiler = "com.github.MatrixDev.Roomigrant:RoomigrantCompiler:$version"
    }

}