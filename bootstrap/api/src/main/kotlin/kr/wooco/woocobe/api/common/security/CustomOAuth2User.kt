package kr.wooco.woocobe.api.common.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    val userId: Long,
    val socialToken: String,
    private val oAuth2User: OAuth2User,
) : OAuth2User {
    override fun getName(): String = oAuth2User.name

    override fun getAttributes(): MutableMap<String, Any> = oAuth2User.attributes

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = oAuth2User.authorities
}
