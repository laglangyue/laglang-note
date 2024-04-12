import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("idea")
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    maven(url = "https://maven-central.storage-download.googleapis.com/maven2/")
    mavenCentral()
    mavenLocal()
}


group = "org.github.demo.grpc"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// IMPORTANT: You probably want the non-SNAPSHOT version of gRPC. Make sure you
// are looking at a tagged version of the example and not "master"!

// Feel free to delete the comment at the next line. It is just for safely
// updating the version in our release process.
val grpcVersion = "1.62.2" // CURRENT_GRPC_VERSION
val protobufVersion = "3.25.1"
val protocVersion = protobufVersion

dependencies {
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-services:${grpcVersion}")
    implementation("io.grpc:grpc-stub:${grpcVersion}")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
    // examples/advanced need this for JsonFormat
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")

    runtimeOnly("io.grpc:grpc-netty-shaded:${grpcVersion}")

    if (JavaVersion.current().isJava9Compatible()) {
        // Workaround for @javax.annotation.Generated
        // see: https://github.com/grpc/grpc-java/issues/3633
        implementation("javax.annotation:javax.annotation-api:1.3.1")
    }


    // test
    testImplementation("io.grpc:grpc-testing:${grpcVersion}")
    testImplementation("io.grpc:grpc-inprocess:${grpcVersion}")
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
    plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without
                // options. Note the braces cannot be omitted, otherwise the
                // plugin will not be added. This is because of the implicit way
                // NamedDomainObjectContainer binds the methods.
                id("grpc") { }
            }
        }
    }
}

// Inform IDEs like IntelliJ IDEA, Eclipse or NetBeans about the generated code.
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

