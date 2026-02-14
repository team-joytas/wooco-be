dependencies {
    implementation(project(":core"))
    implementation(project(":support:common"))

    implementation(libs.spring.boot.starter.data.redis)
}
