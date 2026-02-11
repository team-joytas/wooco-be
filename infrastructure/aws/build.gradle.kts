dependencies {
    implementation(project(":core"))
    implementation(project(":support:common"))

    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:${property("awsS3Version")}")
}
