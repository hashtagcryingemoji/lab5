plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"
var serializationVersion = "0.90.0"
var mockkVersion = "1.13.10"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:${mockkVersion}")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.pdvrieze.xmlutil:core:${serializationVersion}")
    implementation("io.github.pdvrieze.xmlutil:serialization:${serializationVersion}")
    implementation("io.github.pdvrieze.xmlutil:serialization:${serializationVersion}")
    implementation("io.github.pdvrieze.xmlutil:serialization:${serializationVersion}")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
    implementation("org.apache.logging.log4j:log4j-core:2.25.3")

}

application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(17)
}
