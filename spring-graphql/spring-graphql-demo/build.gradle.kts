import com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  id("com.netflix.dgs.codegen") version "5.1.16"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
}

group = "com.toy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
  implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
  implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure:5.0.3")
  implementation("com.github.javafaker:javafaker:1.+")
  implementation("org.yaml:snakeyaml:1.33")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<GenerateJavaTask> {
  generateClient = true
  packageName = "com.toy.springgraphqldemo.generated"
  language = "kotlin"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
//  dependsOn(tasks.withType<GenerateJavaTask>())
}

tasks.withType<Test> {
  useJUnitPlatform()
}
