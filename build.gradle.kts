plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "${property("projectGroup")}"
version = "${property("applicationVersion")}"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.f4b6a3:tsid-creator:${property("tsidCreatorVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:${property("tsidVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${property("jdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${property("jdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${property("jdslVersion")}")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")
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

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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
