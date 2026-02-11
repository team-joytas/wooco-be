plugins {
    kotlin("jvm")
    kotlin("plugin.jpa") apply false
    kotlin("plugin.spring")
    id("org.springframework.boot") apply false
    id("org.springframework.boot.aot") apply false
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
}

allprojects {
    group = "${property("projectGroup")}"
    version = "${property("applicationVersion")}"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks.named<Jar>("jar") {
        enabled = true
    }

    tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")

        implementation("org.springframework:spring-context")
        implementation("org.springframework.boot:spring-boot-autoconfigure")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:${property("kotestVersion")}")
        testImplementation("io.kotest:kotest-framework-datatest:${property("kotestVersion")}")
        testImplementation("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:${property("kotestExtensionsVersion")}")
        testImplementation("io.mockk:mockk:${property("mockkVersion")}")
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

ktlint {
    version.set("${property("ktlintRulesetVersion")}")
}

tasks.register<Copy>("addGitHooks") {
    from(file("${rootProject.rootDir}/scripts/commit-msg"))
    into(file("${rootProject.rootDir}/.git/hooks"))
}

tasks.register<Exec>("installGitHooks") {
    commandLine("chmod", "+x", "${project.rootDir}/.git/hooks/commit-msg")
    dependsOn("addGitHooks")
}

tasks.register<Exec>("uninstallGitHooks") {
    commandLine("rm", "-f", "${project.rootDir}/.git/hooks/commit-msg")
}
