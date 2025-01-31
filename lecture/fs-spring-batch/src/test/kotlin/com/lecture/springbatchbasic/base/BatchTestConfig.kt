package com.lecture.springbatchbasic.base

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = ["com.lecture.springbatchbasic.core.repository"])
@EntityScan(basePackages = ["com.lecture.springbatchbasic.core.domain"])
class BatchTestConfig {
//  @Bean
  fun jobLauncherTestUtils(): JobLauncherTestUtils {
    return JobLauncherTestUtils()
  }
}