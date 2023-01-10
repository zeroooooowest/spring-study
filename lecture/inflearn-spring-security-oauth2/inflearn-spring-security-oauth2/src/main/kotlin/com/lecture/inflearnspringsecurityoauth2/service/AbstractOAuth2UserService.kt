package com.lecture.inflearnspringsecurityoauth2.service

import com.lecture.inflearnspringsecurityoauth2.model.*
import com.lecture.inflearnspringsecurityoauth2.repository.UserRepository
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
abstract class AbstractOAuth2UserService(
  private val userRepository: UserRepository
) {

  fun register(providerUser: ProviderUser, userRequest: OAuth2UserRequest) {

  }

  protected fun providerUser(clientRegistration: ClientRegistration, oAuth2User: OAuth2User): OAuth2ProviderUser {
    return when(clientRegistration.registrationId) {
      "keycloak" -> {
        KeycloakUser(oAuth2User, clientRegistration)
      }
      "google" -> {
        GoogleUser(oAuth2User, clientRegistration)
      }
      "naver" -> {
        NaverUser(oAuth2User, clientRegistration)
      }
      else -> { throw RuntimeException("${clientRegistration.registrationId} is not supported oauth-client-registration") }
    }
  }

  protected fun register(providerUser: OAuth2ProviderUser, oAuth2UserRequest: OAuth2UserRequest) {

  }
}