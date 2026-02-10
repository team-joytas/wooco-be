import org.springframework.boot.gradle.tasks.aot.ProcessAot
import org.springframework.boot.gradle.tasks.aot.ProcessTestAot
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = true

dependencies {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.springframework.boot.aot")

    implementation(project(":core"))
    implementation(project(":support:metric"))
    implementation(project(":support:common"))
    implementation(project(":support:logging"))
    implementation(project(":infrastructure:aws"))
    implementation(project(":infrastructure:rest"))
    implementation(project(":infrastructure:mysql"))
    implementation(project(":infrastructure:redis"))
    implementation(project(":infrastructure:fcm"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springDocVersion")}")
}

// AOT 설정 적용
tasks.named<BootJar>("bootJar") {
    dependsOn("processAot")

    layered {
        enabled = true
    }

    mainClass.set("kr.wooco.woocobe.api.WoocoBeApplicationKt")
}

tasks.named<ProcessAot>("processAot") {
    enabled = true
    dependsOn("classes")
}

tasks.named<ProcessTestAot>("processTestAot") {
    enabled = false
}

// TODO-HONG: Convention plugin 고려해야함

val dockerImageName: String = project.findProperty("imageName")?.toString()
    ?: project.name.toString()
val dockerImageVersion: String = project.findProperty("imageVersion")?.toString()
    ?: project.version.toString()

tasks.register<Exec>("buildImage") {
    description = "Build Docker image for ${project.name}"

    dependsOn("bootJar")

    workingDir(projectDir)

    commandLine(
        "docker",
        "build",
        "-t",
        "$dockerImageName:$dockerImageVersion",
        "-f",
        "Dockerfile",
        ".",
    )

    doFirst {
        println("Building Docker image: $dockerImageName:$dockerImageVersion")
    }
}
