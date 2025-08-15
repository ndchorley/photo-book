plugins {
    kotlin("jvm") version "2.2.0"
    id("application")
    id("com.adarshr.test-logger").version("4.0.0")
    id("com.gradleup.shadow").version("9.0.1")
}

group = "com.xyphias"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:6.15.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-template-handlebars")

    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("org.http4k:http4k-testing-webdriver")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.strikt:strikt-core:0.35.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24)
}

application {
    mainClass = "com.xyphias.photobook.MainKt"
}
