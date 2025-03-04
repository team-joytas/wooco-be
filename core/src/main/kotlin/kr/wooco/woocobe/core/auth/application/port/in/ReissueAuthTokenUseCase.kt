package kr.wooco.woocobe.core.auth.application.port.`in`

import kr.wooco.woocobe.core.auth.application.port.`in`.results.TokenPairResult

interface ReissueAuthTokenUseCase {
    fun reissueAuthToken(refreshToken: String): TokenPairResult
}
