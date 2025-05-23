plugins {
    kotlin("jvm") version "2.1.0"
    application
    id("io.ktor.plugin") version "3.0.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.devtools.ksp") version "2.1.0-1.0.28"
}

group = "com.cashi"
version = "1.0.0"

application {
    mainClass.set("com.cashi.app.MainKt")
}

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.0"
val kotlinVersion = "2.1.0"
val kspVersion = "2.1.0-1.0.28"
val slf4jVersion = "2.0.9"
val logbackVersion = "1.5.13"
val restateVersion = "2.1.0"
val kotestVersion = "5.7.2"
val serializationVersion = "1.6.3"

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.8.1")

    // Logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")

    // Restate Client
    implementation("dev.restate:sdk-kotlin-http:$restateVersion")
    ksp("dev.restate:sdk-api-kotlin-gen:$restateVersion")


    testImplementation("dev.restate:sdk-testing:${restateVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host:${ktorVersion}")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
}

kotlin {
    jvmToolchain(21)
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}
