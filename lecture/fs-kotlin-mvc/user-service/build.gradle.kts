import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-webflux")

  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  runtimeOnly("io.r2dbc:r2dbc-h2")

  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  implementation("at.favre.lib:bcrypt:0.9.0")

  kapt("org.springframework.boot:spring-boot-configuration-processor")
}

