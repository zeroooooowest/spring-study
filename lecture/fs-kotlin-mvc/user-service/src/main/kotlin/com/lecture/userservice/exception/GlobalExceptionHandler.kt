package com.lecture.userservice.exception

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
class GlobalExceptionHandler(
  private val objectMapper: ObjectMapper
): ErrorWebExceptionHandler {
  private val log = KotlinLogging.logger {  }

  override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
    log.error { ex.message }
    val errorResponse = if (ex is ServerException) {
      ErrorResponse(code = ex.code, message = ex.message)
    } else {
      ErrorResponse(code = 500, message = "unexpected server error...")
    }
    with(exchange.response) {
      statusCode = HttpStatus.OK
      headers.contentType = MediaType.APPLICATION_JSON
      val buffer = bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
      writeWith(buffer.toMono()).awaitSingle()
    }
  }
}