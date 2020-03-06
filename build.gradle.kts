import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    java
    kotlin("jvm") version "1.3.50"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.50")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", "1.3.50")
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.3.50")

//    testImplementation("org.junit.vintage", "junit-vintage-engine", version="5.5.0")

    testImplementation("junit", "junit", "4.12")
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.50")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    implementation(kotlin("stdlib-jdk8"))
}

group = "org.kollektions.intervalmap"
version = "1.0-SNAPSHOT"

java.sourceSets["main"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir("src/main/kotlin/")
}

java.sourceSets["test"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir("src/test/kotlin/unit/")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}