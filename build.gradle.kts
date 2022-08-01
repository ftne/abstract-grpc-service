import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.google.protobuf") version "0.8.19"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-netty:" + Version.GRPC)
    implementation("io.grpc:grpc-protobuf:" + Version.GRPC)
    implementation("io.grpc:grpc-stub:" + Version.GRPC)
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-kotlin:" + Version.PROTOBUF)
    implementation("com.google.protobuf:protobuf-java:" + Version.PROTOBUF)
    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        proto {
            srcDir("src/main/resources/proto")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

companion object Version {
    const val GRPC = "1.47.0"
    const val PROTOBUF = "3.21.1"
}