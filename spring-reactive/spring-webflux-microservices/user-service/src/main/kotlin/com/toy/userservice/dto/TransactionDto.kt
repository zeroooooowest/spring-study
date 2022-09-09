package com.toy.userservice.dto

import com.toy.userservice.domain.UserTransaction
import java.time.LocalDateTime

data class TransactionRequestDto(
  val userId: Int,
  val amount: Int
) {
  fun toEntity(): UserTransaction {
    return UserTransaction(
      userId = userId,
      amount = amount,
      createdDate = LocalDateTime.now()
    )
  }
}

data class TransactionResponseDto(
  val userId: Int,
  val amount: Int,
  val status: TransactionStatus
) {
  companion object {
    fun of(requestDto: TransactionRequestDto, status: TransactionStatus): TransactionResponseDto {
      return TransactionResponseDto(
        userId = requestDto.userId,
        amount = requestDto.amount,
        status = status
      )
    }
  }
}

enum class TransactionStatus {
  APPROVED, DECLINED
}