plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.todolistiii"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolistiii"
        minSdk = 21
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    val room_version = "2.6.0" // добавляем зависимость для работы с БД через room
    implementation("androidx.room:room-runtime:$room_version")// добавляем зависимость для работы с БД через room
    annotationProcessor("androidx.room:room-compiler:$room_version")// добавляем зависимость для работы с БД через room
    implementation("androidx.room:room-ktx:$room_version")// добавляем зависимость для работы с БД через room
    testImplementation("junit:junit:4.13.2")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2'") // зависимость для RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")// зависимость для RxJava
    implementation("androidx.room:room-rxjava3:$room_version")// зависимость для room RxJava
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


}