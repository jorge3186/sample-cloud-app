rootProject.name = "sample-cloud-app"

pluginManagement {
    
    repositories {
        jcenter()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            when(requested.id.id) {
                "org.springframework.boot" -> useModule("org.springframework.boot:spring-boot-gradle-plugin:2.1.7.RELEASE")
                "io.spring.dependency-management" -> useModule("org.springframework.boot:spring-boot-gradle-plugin:2.1.7.RELEASE")
            }
        }
    }
}

include(
        "config",
        "discovery",
        "exchange-rates",
        "currency-converter"
)