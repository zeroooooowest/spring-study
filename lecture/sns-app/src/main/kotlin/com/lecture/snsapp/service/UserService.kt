package com.lecture.snsapp.service

import com.lecture.snsapp.domain.User
import com.lecture.snsapp.exception.ApplicationException
import com.lecture.snsapp.exception.ErrorCode
import com.lecture.snsapp.repository.UserRepository
import com.lecture.snsapp.vo.UserResponseVO
import com.lecture.utils.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
  private val passwordEncoder: BCryptPasswordEncoder
) {

  @Value("\${jwt.secret-key}") lateinit var secretKey: String
  @Value("\${jwt.expiry-time-ms}") lateinit var expiryTimeMs: String

  @Transactional
  fun join(username: String, password: String): UserResponseVO {
    userRepository.findByUsername(username)?.let {
      throw ApplicationException(ErrorCode.DUPLICATED_USER_NAME)
    }
    val user = User.of(username = username, password = passwordEncoder.encode(password))
    val savedUser = userRepository.save(user)
    return UserResponseVO.of(savedUser)
  }

  fun login(username: String, password: String): String {
    val user = userRepository.findByUsername(username)
      ?: throw ApplicationException(ErrorCode.LOGIN_FAILED)
    if(!passwordEncoder.matches(password, user.password))
      throw ApplicationException(ErrorCode.LOGIN_FAILED)
    return JwtUtils.generateToken(username, secretKey, expiryTimeMs.toLong())
  }
}