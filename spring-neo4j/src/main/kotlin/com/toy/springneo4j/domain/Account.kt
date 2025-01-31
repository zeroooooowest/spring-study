package com.toy.springneo4j.domain

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("account")
data class Account(
  @Id
  @GeneratedValue
  var id: Long? = null,

  var username: String,

  @Relationship(type = "has")
  val roles: MutableSet<Role> = mutableSetOf()
)