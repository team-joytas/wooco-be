import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.aot) apply false
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
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.spring.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.boot.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.dependency.management.get().pluginId)
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    tasks.named<Jar>("jar") {
        enabled = true
    }

    tasks.named<BootJar>("bootJar") {
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
    from(file("${rootProject.rootDir}/scripts/pre-push"))
    into(file("${rootProject.rootDir}/.git/hooks"))
}

tasks.register<Exec>("installGitHooks") {
    commandLine("chmod", "+x", "${project.rootDir}/.git/hooks/pre-push")
    dependsOn("addGitHooks")
}

tasks.register<Exec>("uninstallGitHooks") {
    commandLine("rm", "-f", "${project.rootDir}/.git/hooks/pre-push")
}
