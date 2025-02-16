import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.Exec
import org.gradle.internal.jvm.Jvm

plugins {
    kotlin("jvm") version "2.1.0"
    id("fabric-loom") version "1.9-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}

repositories {
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    implementation("io.github.humbleui:skija-shared:${project.property("skija_version")}")
    implementation("io.github.humbleui:skija-windows-x64:${project.property("skija_version")}")
    implementation("io.github.humbleui:skija-linux-x64:${project.property("skija_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version"),
            "loader_version" to project.property("loader_version")
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName}" }
    }
}

tasks.shadowJar {
    configurations = listOf(project.configurations.runtimeClasspath.get())
    dependencies {
        include(dependency("io.github.humbleui:skija-windows-x64:${project.property("skija_version")}"))
    }
    from(fileTree("libs") { include("*.jar") })
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.register<Exec>("runClient + RenderDoc") {
    val javaHome = Jvm.current().javaHome

    commandLine = listOf(
        "C:\\Program Files\\RenderDoc\\renderdoccmd.exe",
        "capture",
        "--opt-api-validation", // Remove if you don't want api validation
        "--opt-api-validation-unmute", // Remove if you don't want api validation
        "--opt-hook-children",
        "--wait-for-exit",
        "--working-dir",
        ".",
        "$javaHome\\bin\\java.exe",
        "-Xmx64m",
        "-Xms64m",
        //"-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005", // Uncomment for remote debug
        "-Dorg.gradle.appname=gradlew",
        "-Dorg.gradle.java.home=$javaHome",
        "-classpath",
        "gradle\\wrapper\\gradle-wrapper.jar",
        "org.gradle.wrapper.GradleWrapperMain",
        "runClient",
    )
}
