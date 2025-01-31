package com.lecture.fsmysql.domain.member.repository

import com.lecture.fsmysql.domain.member.entity.Member
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class MemberRepository(
  private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {

  companion object {
    private const val TABLE = "Member"

    val ROW_MAPPER = RowMapper { resultSet, rowNum ->
      Member(
        id = resultSet.getLong("id"),
        nickname = resultSet.getString("nickname"),
        email = resultSet.getString("email"),
        birthday = resultSet.getObject("birthday", LocalDate::class.java),
        createdAt = resultSet.getObject("createdAt", LocalDateTime::class.java)
      )
    }
  }

  fun save(member: Member): Member {
    return member.id?.let {
      update(member)
    } ?: insert(member)
  }

  private fun insert(member: Member): Member {
    val simpleJdbcInsert = SimpleJdbcInsert(namedParameterJdbcTemplate.jdbcTemplate)
      .withTableName(TABLE)
      .usingGeneratedKeyColumns("id")
    val parameter = BeanPropertySqlParameterSource(member)
    val id = simpleJdbcInsert.executeAndReturnKey(parameter).toLong()
    return Member(
      id = id,
      nickname = member.nickname,
      email = member.email,
      birthday = member.birthday,
    )
  }

  private fun update(member: Member): Member {
    val sql = String.format("""
      update %s 
      set email = :email, nickname = :nickname, birthday = :birthday
      where id = :id
    """.trimIndent(), TABLE)
    val params = BeanPropertySqlParameterSource(member)
    namedParameterJdbcTemplate.update(sql, params)
    return member
  }

  fun findById(id: Long): Member? {
    val sql = String.format("select * from %s where id = :id", TABLE)
    val parameter = MapSqlParameterSource()
      .addValue("id", id)
    return namedParameterJdbcTemplate.queryForObject(sql, parameter, ROW_MAPPER)
  }

  fun findByAllIdIn(ids: List<Long>): List<Member> {
    if(ids.isEmpty())
      return listOf()
    val sql = String.format("select * from %s where id in (:ids)", TABLE)
    val parameter = MapSqlParameterSource().addValue("ids", ids)
    return namedParameterJdbcTemplate.query(sql, parameter, ROW_MAPPER).toList()
  }
}