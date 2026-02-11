dependencies {
    implementation(project(":support:common"))

    implementation(libs.micrometer.prometheus)
    implementation(libs.spring.boot.starter.actuator)
}
