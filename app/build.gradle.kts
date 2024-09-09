plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Room 관련 플러그인
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
    id("kotlin-kapt")
}

android {
    namespace = "com.project.animalface_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.animalface_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    // 뷰 바인딩 활성화
    viewBinding {
        enable = true
    }
}

dependencies {

    // SplashScreen API
//    implementation(libs.androidx.core.splashscreen)
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // 코루틴 추가
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.12.0")

    // Retrofit and Gson for API and JSON parsing
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.9")

    // OkHttp for networking
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Room for local database
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")

    // Paging
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.runtime.ktx)

    // Lifecycle and ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    // Activity and Fragment
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    // WebView
    implementation("androidx.webkit:webkit:1.8.0")

    // Core and AppCompat
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // RecyclerView and CardView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")

    // Time handling
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.4")

    // Unit and UI testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
