package kr.wooco.woocobe.core.auth.application.port.`in`

import kr.wooco.woocobe.core.auth.application.port.`in`.results.TokenPairResult

fun interface SocialLoginUseCase {
    fun socialLogin(
        code: String,
        authCodeId: String,
        socialType: String,
    ): TokenPairResult
}
