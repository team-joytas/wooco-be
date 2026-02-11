import org.springframework.boot.gradle.tasks.aot.ProcessAot
import org.springframework.boot.gradle.tasks.aot.ProcessTestAot
import org.springframework.boot.gradle.tasks.bundling.BootJar

apply(plugin = "org.springframework.boot.aot")

dependencies {
    implementation(project(":core"))
    implementation(project(":support:metric"))
    implementation(project(":support:common"))
    implementation(project(":support:logging"))
    implementation(project(":infrastructure:aws"))
    implementation(project(":infrastructure:rest"))
    implementation(project(":infrastructure:mysql"))
    implementation(project(":infrastructure:redis"))
    implementation(project(":infrastructure:fcm"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.oauth2.client)

    runtimeOnly(libs.bundles.jjwt.runtime)
    implementation(libs.jjwt.api)

    implementation(libs.springdoc.openapi)

    testImplementation(libs.spring.security.test)
}

// AOT 설정 적용
tasks.named<BootJar>("bootJar") {
    enabled = true
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
