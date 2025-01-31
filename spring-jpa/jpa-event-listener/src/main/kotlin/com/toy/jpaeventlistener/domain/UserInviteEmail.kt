package com.toy.jpaeventlistener.domain

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_user_invite_email")
class UserInviteEmail(
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  var id: String? = null,

  val email: String
) {
  override fun toString(): String {
    return "UserInviteEmail(id=$id, email='$email')"
  }
}

interface UserInviteEmailRepository: CrudRepository<UserInviteEmail, String> {
  fun findByEmail(email: String): UserInviteEmail?
}