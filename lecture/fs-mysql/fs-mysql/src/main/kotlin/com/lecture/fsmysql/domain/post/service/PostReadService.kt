package com.lecture.fsmysql.domain.post.service

import com.lecture.fsmysql.common.CursorRequest
import com.lecture.fsmysql.common.PageCursor
import com.lecture.fsmysql.domain.post.dto.DailyPostCountRequestDto
import com.lecture.fsmysql.domain.post.dto.DailyPostCountResponseDto
import com.lecture.fsmysql.domain.post.dto.PostDto
import com.lecture.fsmysql.domain.post.entity.Post
import com.lecture.fsmysql.domain.post.repository.PostLikeRepository
import com.lecture.fsmysql.domain.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostReadService(
  private val postRepository: PostRepository,
  private val postLikeRepository: PostLikeRepository
) {

  fun getDailyPostCount(requestDto: DailyPostCountRequestDto): List<DailyPostCountResponseDto> {
    return postRepository.groupByCreatedDate(requestDto)
  }

  fun getPosts(memberId: Long, pageable: Pageable): Page<PostDto> {
    return postRepository.findAllByMemberId(memberId, pageable)
      .map { toDto(it) }
  }

  private fun toDto(post: Post): PostDto {
    return PostDto(
      post.id,
      post.content,
      post.createdAt,
      postLikeRepository.count(post.id)
    )
  }

  fun getPosts(memberId: Long, cursorRequest: CursorRequest): PageCursor<Post> {
    val posts = findPosts(memberId, cursorRequest)
    val nextKey = posts.minOfOrNull { it.id } ?: CursorRequest.DEFAULT_KEY
    return PageCursor(cursorRequest.next(nextKey), posts)
  }

  fun getPosts(memberIds: List<Long>, cursorRequest: CursorRequest): PageCursor<Post> {
    val posts = findPosts(memberIds, cursorRequest)
    val nextKey = posts.minOfOrNull { it.id } ?: CursorRequest.DEFAULT_KEY
    return PageCursor(cursorRequest.next(nextKey), posts)
  }

  fun getPost(id: Long): Post {
    return postRepository.findById(id, false) ?: throw RuntimeException("not found...")
  }

  fun getPosts(ids: List<Long>): List<Post> {
    return postRepository.findAllByInIds(ids)
  }

  private fun findPosts(memberId: Long, cursorRequest: CursorRequest): List<Post> {
    return if(cursorRequest.hasKey())
      postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key!!, memberId, cursorRequest.size)
    else
      postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size)
  }

  private fun findPosts(memberIds: List<Long>, cursorRequest: CursorRequest): List<Post> {
    return if(cursorRequest.hasKey())
      postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key!!, memberIds, cursorRequest.size)
    else
      postRepository.findAllByMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size)
  }
}