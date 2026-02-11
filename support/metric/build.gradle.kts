dependencies {
    implementation(project(":support:common"))

    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}
