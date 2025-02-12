import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = true
bootJar.destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))

dependencies {
    apply(plugin = "org.springframework.boot")

    implementation(project(":core"))
    implementation(project(":support:logging"))
    implementation(project(":support:common"))
    implementation(project(":infrastructure:aws"))
    implementation(project(":infrastructure:jwt"))
    implementation(project(":infrastructure:rest"))
    implementation(project(":infrastructure:mysql"))
    implementation(project(":infrastructure:redis"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springDocVersion")}")
}
