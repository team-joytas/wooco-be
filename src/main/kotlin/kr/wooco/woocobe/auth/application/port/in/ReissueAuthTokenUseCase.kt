package kr.wooco.woocobe.auth.application.port.`in`

import kr.wooco.woocobe.auth.application.port.`in`.results.TokenPairResult

interface ReissueAuthTokenUseCase {
    fun reissueAuthToken(refreshToken: String): TokenPairResult
}
