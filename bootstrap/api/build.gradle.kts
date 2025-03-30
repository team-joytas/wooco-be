import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = true
bootJar.destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))

dependencies {
    apply(plugin = "org.springframework.boot")

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
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springDocVersion")}")
}
