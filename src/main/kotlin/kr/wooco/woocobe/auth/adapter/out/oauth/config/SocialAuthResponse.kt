package kr.wooco.woocobe.auth.adapter.out.oauth.config

import kr.wooco.woocobe.auth.domain.vo.SocialInfo

interface SocialAuthResponse {
    fun toDomain(): SocialInfo
}
