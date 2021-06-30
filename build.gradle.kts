import org.apache.tools.ant.filters.ReplaceTokens
plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

group = "net.gigaclub"
version = "1.17.1.0.0"

apply(plugin = "java")

group = project.group
version = project.version

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_16
}


val spigotPluginsDir: String? by project
val GITHUB_PACKAGES_USERID: String? by project
val GITHUB_PACKAGES_IMPORT_TOKEN: String? by project

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "GitHubPackagesTranslation"
        url = uri("https://maven.pkg.github.com/gigaclub/translationapi")
        metadataSources {
            mavenPom()
            artifact()
        }
        credentials {
            username = GITHUB_PACKAGES_USERID
            password = GITHUB_PACKAGES_IMPORT_TOKEN
        }
    }
    maven {
        name = "GitHubPackagesBuilderSystem"
        url = uri("https://maven.pkg.github.com/gigaclub/buildersystemapi")
        metadataSources {
            mavenPom()
            artifact()
        }
        credentials {
            username = GITHUB_PACKAGES_USERID
            password = GITHUB_PACKAGES_IMPORT_TOKEN
        }
    }
    maven {
        name = "cloudnet-snapshots"
        url = uri("https://repo.cloudnetservice.eu/repository/snapshots/")
    }
}

dependencies {
    implementation("net.gigaclub:translationapi:14.0.1.0.3")
    implementation("net.gigaclub:buildersystemapi:14.0.1.0.0")
    implementation("de.dytanic.cloudnet:cloudnet-bridge:3.4.0-SNAPSHOT")
    implementation("de.dytanic.cloudnet:cloudnet-driver:3.4.0-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
}

tasks {
    // If you open resources/plugins.yml you will see "@version@" as the version this code replaces this
    processResources {
        from(sourceSets["main"].resources) {
            val tokens = mapOf("version" to version)
            filter(ReplaceTokens::class, mapOf("tokens" to tokens))
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}