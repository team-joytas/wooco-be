package kr.wooco.woocobe.auth.infrastructure.client

import kr.wooco.woocobe.auth.domain.model.SocialAuth

interface SocialAuthResponse {
    fun toDomain(): SocialAuth
}
