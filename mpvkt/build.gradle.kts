plugins {
    kotlin("jvm")
}

group = "tel.jeelpa"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("net.java.dev.jna:jna-platform:5.15.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}