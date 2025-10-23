plugins {
    kotlin("jvm") version "2.2.20"
    id("application")
    id("com.adarshr.test-logger").version("4.0.0")
    id("com.gradleup.shadow").version("9.2.2")
}

group = "com.xyphias"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.50.3.0")
    implementation("org.flywaydb:flyway-core:11.14.1")

    implementation(platform("org.http4k:http4k-bom:6.18.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-template-handlebars")

    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("org.http4k:http4k-testing-webdriver")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
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
