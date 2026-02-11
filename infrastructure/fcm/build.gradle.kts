dependencies {
    implementation(project(":core"))

    implementation("com.google.firebase:firebase-admin:${property("firebaseAdminVersion")}")
}
