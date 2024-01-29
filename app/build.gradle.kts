plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp" )
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}


android {

    namespace = "com.nullpointer.runningcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nullpointer.running"
        minSdk = 21
        targetSdk = 34
        versionCode = 4
        versionName = "3.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }

        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    applicationVariants.all {
        addJavaSourceFoldersToModel(
            File(buildDir, "generated/ksp/$name/kotlin")
        )
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-service:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")


    // * mp graph
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.cardview:cardview:1.0.0")

    // * coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // *lottie compose
    implementation("com.airbnb.android:lottie-compose:5.1.1")

    // * timber
    implementation("com.orhanobut:logger:2.2.0")
    implementation("com.jakewharton.timber:timber:5.0.1")


    // * dagger hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")


    // * room
    val roomVersion = "2.5.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // * save state
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")

    // * number picker
    implementation("com.github.StephenVinouze:MaterialNumberPicker:1.0.7")

    // * image compressor
    implementation("com.github.Shouheng88:compressor:1.6.0")

    // * splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // * shimmer effect
    implementation ("com.valentinilk.shimmer:compose-shimmer:1.0.3")

    // * navigation
    val destinationsVersion = "1.8.42-beta"
    implementation("io.github.raamcosta.compose-destinations:core:$destinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destinationsVersion")


    // * data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // * gson
    implementation("com.google.code.gson:gson:2.10.1")

    // * maps
    implementation("com.google.maps.android:maps-ktx:3.4.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.4.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // * play services
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.lifecycle:lifecycle-service:2.6.2")

    // * select color picker
    implementation("com.github.skydoves:colorpicker-compose:1.0.0")

    // * polyUtils
    implementation("com.google.maps.android:android-maps-utils:2.2.3")

    // * permissions
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")

    // * pagination
    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:3.2.1")
    implementation("androidx.room:room-paging:$roomVersion")

    // * kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // * Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")


}