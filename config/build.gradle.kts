dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-parent:2.1.7.RELEASE")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE")
    }
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-config-server")

}