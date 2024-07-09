plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.fetch.androidtakehome"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fetch.androidtakehome"
        minSdk = 27
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


}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    testImplementation(libs.junit)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.core.testing.v210)
    testImplementation(libs.mockito.core)
    testImplementation(libs.core)
    testImplementation(libs.core.testing)
    testImplementation(libs.robolectric.v451)
    testImplementation(libs.powermock.module.junit4)
    testImplementation(libs.powermock.api.mockito2)


    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hamcrest.library)

}