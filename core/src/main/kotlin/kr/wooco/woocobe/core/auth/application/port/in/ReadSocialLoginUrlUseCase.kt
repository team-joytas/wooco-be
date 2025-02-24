package kr.wooco.woocobe.core.auth.application.port.`in`

import kr.wooco.woocobe.core.auth.application.port.`in`.results.SocialLoginUrlResult

fun interface ReadSocialLoginUrlUseCase {
    fun readSocialLoginUrl(socialType: String): SocialLoginUrlResult
}
