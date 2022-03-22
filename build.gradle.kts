import org.apache.tools.ant.filters.ReplaceTokens
plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.gigaclub"
version = "1.18.2.1.0.0"

apply(plugin = "java")

group = project.group
version = project.version

val spigotPluginsDir: String? by project
val GITHUB_PACKAGES_USERID: String by project
val GITHUB_PACKAGES_IMPORT_TOKEN: String by project

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
        name = "extendedclip"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
	maven {
        name = "GitHubPackages"
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
        name = "releases"
        url = uri("https://repo.cloudnetservice.eu/repository/releases/")
    }


}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("org.jetbrains:annotations:23.0.0")
    annotationProcessor("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
	implementation("net.gigaclub:translationapi:14.0.1.0.1")
    implementation("de.dytanic.cloudnet:cloudnet-bridge:3.4.3-RELEASE")
    implementation("de.dytanic.cloudnet:cloudnet-driver:3.4.3-RELEASE")
    implementation("net.gigaclub:buildersystemapi:14.0.1.0.6")
    implementation("net.gigaclub:translationapi:14.0.1.0.3")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
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

    build {
        dependsOn(shadowJar)
    }

}
