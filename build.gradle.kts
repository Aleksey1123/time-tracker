plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
}

group = "com.time-tracker"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.2")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")



    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:5.10.0")

}

tasks {
    forkedSpringBootRun {
        doNotTrackState("See https://github.com/springdoc/springdoc-openapi-gradle-plugin/issues/102")
    }

    generateOpenApiDocs {
        outputFileName.set("openapi.yaml")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

