buildscript {

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.7.RELEASE")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.8.RELEASE")
    }

}

subprojects {

    repositories {
        jcenter()
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

}