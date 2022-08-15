package com.toy.consumer.consumer

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class DummyConsumer() {
  private val log = LoggerFactory.getLogger(javaClass)

  @RabbitListener(queues = ["q.dummy"])
  fun listener(message: DummyConsumer) {
    log.info("message: {}", message)
  }
}