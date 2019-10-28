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

    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.50")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

group = "com.tgt.trans.common"
version = "1.0-SNAPSHOT"

