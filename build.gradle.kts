plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.purpurmc.purpur:purpur-api:1.19.3-R0.1-SNAPSHOT")
    api("net.mamoe", "mirai-core", "2.14.0")
    compileOnly("me.clip", "placeholderapi", "2.11.2")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}