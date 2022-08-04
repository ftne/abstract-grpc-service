import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.19")
    }
}

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.google.protobuf") version "0.8.19"
    `maven-publish`
}

group = "com.ftne.absys"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "grpc-hello-api"
            version = version

            from(components["java"])
        }
    }
}

sourceSets {
    val main by getting { }
    main.java.srcDirs("build/generated/source/proto/main/java")
    main.java.srcDirs("build/generated/source/proto/main/kotlin")
    main.java.srcDirs("build/generated/source/proto/main/grpc")
    main.java.srcDirs("build/generated/source/proto/main/grpckt")
}

dependencies {
    implementation("io.grpc:grpc-netty:" + Version.GRPC)
    implementation("io.grpc:grpc-protobuf:" + Version.GRPC)
    implementation("io.grpc:grpc-stub:" + Version.GRPC)
    implementation("io.grpc:grpc-kotlin-stub:" + Version.GRPC_KT_STUB)
    implementation("com.google.protobuf:protobuf-kotlin:" + Version.PROTOBUF)
    implementation("com.google.protobuf:protobuf-java:" + Version.PROTOBUF)
    testImplementation(kotlin("test"))
}

protobuf {
    protoc {
        artifacts {
            artifact = "com.google.protobuf:protoc:${Version.PROTOBUF}"
        }
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${Version.GRPC}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${Version.GRPC_KT_STUB}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("src/main/resources/proto")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
}

companion object Version {
    const val GRPC = "1.47.0"
    const val PROTOBUF = "3.21.1"
    const val GRPC_KT_STUB = "1.3.0"
}