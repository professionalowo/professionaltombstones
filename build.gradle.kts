import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal inline fun <reified T> Project.property(name: String) = property(name) as T
internal operator fun Project.get(name:String) = property(name)

plugins {
    id("fabric-loom") version "1.9-SNAPSHOT"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}
version = project.version

group = project.property<String>("maven_group")

base {
    project.run {
        val name = property<String>("mod_id")
        val version = property<String>("mod_version")
        val mcVersion = property<String>("minecraft_version")

        archivesName.set("$name-$version+mc$mcVersion")
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create(project.property<String>("mod_id")) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets["client"])
        }
    }
}

fabricApi {
    configureDataGeneration() {
        client = true
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project["fabric_version"]}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project["fabric_kotlin_version"]}")

    modImplementation("com.terraformersmc:modmenu:${project["modmenu_version"]}")

    testImplementation("net.fabricmc:fabric-loader-junit:${project["loader_version"]}")

    testImplementation(kotlin("test"))
}

tasks.processResources {
    val version = project.property<String>("mod_version")
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        JavaVersion.VERSION_21
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.extensions.getByType<BasePluginExtension>().archivesName.get()}" }
    }
}

// Configure the Maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.extensions.getByType<BasePluginExtension>().archivesName.get()
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
