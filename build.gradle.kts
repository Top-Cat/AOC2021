import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.top_cat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
}

tasks.test {
    useJUnit()
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}