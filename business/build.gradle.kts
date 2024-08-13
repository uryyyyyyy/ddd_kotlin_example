plugins {
    id("org.jetbrains.kotlin.jvm")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    api("io.arrow-kt:arrow-fx-coroutines-jvm:1.2.4")
    api("org.slf4j:slf4j-api:2.0.13")
    // for junit5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}
