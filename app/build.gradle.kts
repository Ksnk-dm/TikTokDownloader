plugins {

    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.ksnk.tiktokdownloader"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ksnk.tiktokdownloader"
        minSdk = 30
        targetSdk = 34
        versionCode = 6
        versionName = "1.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    val lifecycle_version = "2.7.0"
    val retrofit_version = "2.9.0"
    val coroutine_version = "1.7.3"
    val room_version = "2.6.1"
    val koin_version = "3.4.0"
    val navigation_version = "2.7.7"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("io.insert-koin:koin-android:3.4.0")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Koin
    implementation("io.insert-koin:koin-android:$koin_version")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-ktx:1.8.2")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutine_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")

    //Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")

    //SwipeRefreshlayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")
    implementation("org.greenrobot:eventbus:3.2.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")

    implementation("io.insert-koin:koin-androidx-navigation:$koin_version")

    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.jakewharton.timber:timber:5.0.1")

    implementation ("androidx.media3:media3-exoplayer:1.3.0")
    implementation ("androidx.media3:media3-exoplayer-dash:1.3.0")
    implementation ("androidx.media3:media3-ui:1.3.0")

    implementation ("com.google.android.gms:play-services-ads:23.0.0")
}