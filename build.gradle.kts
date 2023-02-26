plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.encinet"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        name = "aliyun"
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenCentral()
    maven {
        name = "spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/")
    }
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
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

tasks {
    processResources {
        filesMatching("plugin.yml") {
            // 删除注释,你可以返回null以删除整行，但是IDEA有bug会报错，故而返回了""
            filter {
                if (it.trim().startsWith("#")) "" else it
            }
        }
    }
}