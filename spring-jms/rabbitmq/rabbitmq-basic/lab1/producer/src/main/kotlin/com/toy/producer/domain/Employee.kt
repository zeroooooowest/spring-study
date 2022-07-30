package com.toy.producer.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Employee (
   var id: String,
   var name: String,
   @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   var createdDate: LocalDateTime
)