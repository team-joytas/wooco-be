apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    implementation(project(":core"))
    implementation(project(":support:common"))

    runtimeOnly(libs.mysql.connector)
    implementation(libs.bundles.flyway)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.tsid.creator)
    implementation(libs.bundles.jdsl)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
