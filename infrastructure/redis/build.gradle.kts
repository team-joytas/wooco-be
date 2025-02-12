import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

dependencies {
    implementation(project(":core"))

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
