plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp" )
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("com.github.ben-manes.versions")
}


android {

    namespace = "com.nullpointer.runningcompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nullpointer.running"
        minSdk = 21
        targetSdk = 36
        versionCode = 6
        versionName = "4.0.2"

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

    // Compose versions are kept in sync through the BOM.
    // Compatible with the current AGP 8.11.1 / compileSdk 36 toolchain.
    val composeBom = platform("androidx.compose:compose-bom:2025.06.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.2")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.lifecycle:lifecycle-service:2.9.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.8.3")


    // * mp graph
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.cardview:cardview:1.0.0")

    // * coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // *lottie compose
    implementation("com.airbnb.android:lottie-compose:6.6.7")

    // * timber
    implementation("com.orhanobut:logger:2.2.0")
    implementation("com.jakewharton.timber:timber:5.0.1")


    // * dagger hilt
    val hiltVersion = "2.57"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")


    // * room
    val roomVersion = "2.7.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // * save state
    implementation("androidx.savedstate:savedstate-ktx:1.3.1")

    // * number picker
    implementation("com.github.StephenVinouze:MaterialNumberPicker:1.1.0")

    // * image compressor
    implementation("com.github.Shouheng88:compressor:1.6.0")

    // * splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // * shimmer effect
    implementation ("com.valentinilk.shimmer:compose-shimmer:1.3.3")

    // * navigation
    val destinationsVersion = "1.8.42-beta"
    implementation("io.github.raamcosta.compose-destinations:core:$destinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destinationsVersion")


    // * data store
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // * gson
    implementation("com.google.code.gson:gson:2.13.1")

    // * maps
    implementation("com.google.maps.android:maps-ktx:5.2.0")
    implementation("com.google.maps.android:maps-utils-ktx:5.2.0")
    implementation("androidx.fragment:fragment-ktx:1.8.8")

    // * play services
    implementation("com.google.android.gms:play-services-maps:19.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // * select color picker
    implementation("com.github.skydoves:colorpicker-compose:1.1.2")

    // * polyUtils
    implementation("com.google.maps.android:android-maps-utils:3.14.0")

    // * permissions
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    // * pagination
    val pagingVersion = "3.3.6"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:$pagingVersion")
    implementation("androidx.room:room-paging:2.7.2")

    // * kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    // * Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    implementation("androidx.compose.material:material-icons-extended")

}
