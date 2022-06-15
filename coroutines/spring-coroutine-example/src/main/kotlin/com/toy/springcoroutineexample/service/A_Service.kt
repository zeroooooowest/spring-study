package com.toy.springcoroutineexample.service

import kotlinx.coroutines.delay
import org.springframework.stereotype.Service

@Service
class A_Service {

  suspend fun executeSuspend(): String {
    delay(1000L)
    return "A_Service_OK"
  }

  fun executeNormal(): String {
    Thread.sleep(1000L)
    return "B_Service_OK"
  }
}