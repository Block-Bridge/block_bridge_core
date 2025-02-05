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
    id("maven-publish")
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
    implementation("org.eclipse.jetty:jetty-server:11.0.0")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.0")
}
val targetJavaVersion = 21
java {
    withSourcesJar()
    withJavadocJar()
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}