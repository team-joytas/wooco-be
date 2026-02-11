plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.boot.aot) apply false
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.ktlint)
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
        implementation(rootProject.libs.kotlin.reflect)
        implementation(rootProject.libs.jackson.module.kotlin)
        implementation(rootProject.libs.kotlin.logging)

        implementation(rootProject.libs.spring.context)
        implementation(rootProject.libs.spring.boot.autoconfigure)
        annotationProcessor(rootProject.libs.spring.boot.configuration.processor)

        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
        testImplementation(rootProject.libs.spring.boot.starter.test)
        testImplementation(rootProject.libs.kotlin.test.junit5)
        testImplementation(rootProject.libs.bundles.kotest)
        testImplementation(rootProject.libs.mockk)
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
    version.set(libs.versions.ktlint.ruleset.get())
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
