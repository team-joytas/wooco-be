import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

dependencies {
    implementation(project(":core"))
    implementation(project(":support:common"))

    implementation("org.springframework:spring-context")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:${property("awsS3Version")}")
}
