package kr.wooco.woocobe.auth.application.port.`in`

import kr.wooco.woocobe.auth.application.port.`in`.results.TokenPairResult

fun interface SocialLoginUseCase {
    fun socialLogin(
        code: String,
        authCodeId: String,
        socialType: String,
    ): TokenPairResult
}
