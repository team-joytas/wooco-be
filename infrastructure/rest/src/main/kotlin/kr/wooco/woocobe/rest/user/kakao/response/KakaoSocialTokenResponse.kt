package kr.wooco.woocobe.rest.auth.kakao.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoSocialTokenResponse(
    val accessToken: String,
    val refreshToken: String?,
)
