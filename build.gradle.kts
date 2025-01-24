val coreVersion:        String by project
val jsonVersion:        String by project
val buildStatus:        String by project
val buildNumber:        String by project

val junitVersion:       String by project
val apacheVersion:      String by project

val logbackVersion:     String by project
val group:              String by project

plugins {
    id("java")
}

version = "$coreVersion-$buildStatus.$buildNumber"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

//    testImplementation("commons-io:commons-io:${apacheVersion}")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.json:json:$jsonVersion")
}

tasks.test {
    useJUnitPlatform()
}