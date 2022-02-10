plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

description = "testplugin"
version = "1.181.1"

tasks {
    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("plugin.yml") {
                expand(
                    "version" to project.version
                )
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven {
        name = "eldonexus"
        // url = uri("https://eldonexus.de/repository/maven-snapshots/")
        url = uri("https://eldonexus.de/repository/maven-releases/")
    }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
}

dependencies {
    implementation("co.aikar:acf-bukkit:0.5.0-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.github.yannicklamprecht:worldborderapi:1.181.1")
}
