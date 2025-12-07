plugins {
    id("java")
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jfree:jfreechart:1.5.4")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
    }
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(files("build/classes/java/main"))
    executionData.setFrom(files("build/jacoco/test.exec"))
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)

    violationRules {
        rule {
            element = "CLASS"
            includes = listOf("resolver.Resolver")
            excludes = listOf("**/*\$*")

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }

            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
        }
    }

    classDirectories.setFrom(files("build/classes/java/main"))
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(files("build/jacoco/test.exec"))
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}
