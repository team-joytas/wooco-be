import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

dependencies {
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    implementation(project(":core"))
    implementation(project(":support:common"))

    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.f4b6a3:tsid-creator:${property("tsidCreatorVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${property("jdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${property("jdslVersion")}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${property("jdslVersion")}")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
