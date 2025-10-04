import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.android.gms.oss-licenses-plugin")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.aiden.vokamoka"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aiden.vokamoka"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        // compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.all {
        val variant = this
        val currentDate = Date();
        val formattedDate = SimpleDateFormat("yyyy_MM_dd").format(currentDate)
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                if(output.outputFile != null){
                    if(output.outputFile.name.endsWith(".apk") || output.outputFile.name.endsWith(".aab")){
                        val appPrefix = "voka_moka"
                        val versionName = variant.versionName
                        val buildType = variant.buildType.name
                        val outputName = "${appPrefix}_${buildType}_${formattedDate}_${versionName}.apk"
                        output.outputFileName = outputName
                    }
                }
            }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // multi dex
    val multi_dex_version = "2.0.1"
    implementation("androidx.multidex:multidex:$multi_dex_version")

    // room implements
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Room의 Kotlin 확장 (선택 사항)
    // kapt("androidx.room:room-compiler:$room_version") // Room 애노테이션 프로세서 (kapt 구성)
    // ksp("androidx.room:room-compiler:$room_version") // Room 애노테이션 프로세서 (kapt 구성)
    implementation("androidx.room:room-paging:$room_version") // 페이징 의존성

    // View Model과 같은 클래스의 의존성 주입을 위한 라이브러리 (Hilt)
    // 코드 생성을 간편히 관리해줌, MVVM 디자인 패턴에 잘 어울림.
    // Room 과 궁합이 좋아보여서 도입.
    val hlit_version = "2.57.2"
    implementation("com.google.dagger:hilt-android:$hlit_version")
    kapt("com.google.dagger:hilt-android-compiler:$hlit_version")

    // for paging
    val paging_version ="3.3.6"
    implementation("androidx.paging:paging-runtime:$paging_version")

    // rxjava, rxkotlin implements
    val rx_java_version = "3.1.8"
    val rx_kotlin_version = "3.0.1"
    implementation("io.reactivex.rxjava3:rxjava:$rx_java_version")
    implementation("io.reactivex.rxjava3:rxkotlin:$rx_kotlin_version") // rx kotlin
    val rx_android_version = "3.0.2";
    implementation("io.reactivex.rxjava3:rxandroid:$rx_android_version")

    // ViewModel implements
    val lifecycle_version = "2.8.7"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    // Annotation processor
    // kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")


    // navigation implements
    val nav_version = "2.8.6"
    val nav_serialize_version = "1.7.3"
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$nav_serialize_version")

    val gson_version = "2.10.1"
    // gson implements
    implementation("com.google.code.gson:gson:$gson_version")

    /**
     * TODO.. ViewPager2 사용시 해제
     *  implementation 'androidx.viewpager2:viewpager2:1.0.0-beta03'
     */
    //flex box
    val flex_version = "3.0.0"
    implementation("com.google.android.flexbox:flexbox:$flex_version")

    // https://developers.google.com/android/guides/opensource?hl=ko#kotlin-dsl
    val oss_version = "17.0.1"
    implementation("com.google.android.gms:play-services-oss-licenses:$oss_version")

    // admobs
    val admob_version = "22.6.0"
    implementation("com.google.android.gms:play-services-ads:$admob_version")

    val app_update_version = "2.1.0"
    implementation("com.google.android.play:app-update:$app_update_version")
    implementation("com.google.android.play:app-update-ktx:$app_update_version") // for kotlin

    // swiper
    val swiper_version = "1.1.0"
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$swiper_version")


}