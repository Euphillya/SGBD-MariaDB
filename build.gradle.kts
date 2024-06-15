plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "fr.euphyllia"
version = "1.3"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.logging.log4j:log4j-api:2.22.1")
    compileOnly("org.apache.logging.log4j:log4j-core:2.22.1")
    compileOnly("org.mariadb.jdbc:mariadb-java-client:3.3.2")
    compileOnly("com.zaxxer:HikariCP:5.1.0")
    implementation("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create("gpr", MavenPublication::class) {
            from(components["java"])
        }
    }
}
