plugins {
    id("com.github.ben-manes.versions") version "0.51.0"
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

allprojects {
    version = "0.1"
    group = "ddd_example"

    repositories {
        mavenCentral()
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "21"
            targetCompatibility = "21"
        }
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile> {
            compilerOptions {
                freeCompilerArgs =
                    listOf(
                        "-Xjsr305=strict",
                        "-Xcontext-receivers",
                        "-opt-in=kotlin.contracts.ExperimentalContracts",
                    )
                jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
            }
        }
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(false)
        ignoreFailures.set(false)
// 		enableExperimentalRules.set(true)

        filter {
            exclude { it.file.path.contains("/generated/") }
        }
    }
}
