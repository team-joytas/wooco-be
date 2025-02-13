import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

dependencies {
    implementation(project(":core"))
    implementation(project(":support:common"))

    // rest
    implementation("org.springframework.boot:spring-boot-starter-web")
}
