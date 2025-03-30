import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

dependencies {
    implementation("com.github.f4b6a3:tsid-creator:${property("tsidCreatorVersion")}")
}
