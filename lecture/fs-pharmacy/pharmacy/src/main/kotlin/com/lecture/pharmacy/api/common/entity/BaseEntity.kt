package com.lecture.pharmacy.api.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

  @CreatedDate
  @Column(updatable = false)
  var createdDate: LocalDateTime? = null,

  @LastModifiedDate
  var updatedDate: LocalDateTime? = null
)