package kr.wooco.woocobe.rest.auth

import kr.wooco.woocobe.core.auth.domain.vo.SocialInfo

interface SocialAuthResponse {
    fun toDomain(): SocialInfo
}
