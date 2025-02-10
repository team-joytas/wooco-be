package kr.wooco.woocobe.auth.application.port.`in`

import kr.wooco.woocobe.auth.application.port.`in`.results.SocialLoginUrlResult

fun interface ReadSocialLoginUrlUseCase {
    fun readSocialLoginUrl(socialType: String): SocialLoginUrlResult
}
