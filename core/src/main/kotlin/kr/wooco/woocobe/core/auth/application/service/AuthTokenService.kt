package kr.wooco.woocobe.core.auth.application.service

import kr.wooco.woocobe.core.auth.application.port.`in`.ExtractTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.LogoutUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.ReissueAuthTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.results.TokenPairResult
import kr.wooco.woocobe.core.auth.application.port.out.AuthTokenPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.TokenProviderPort
import org.springframework.stereotype.Service

// TODO: 로직 전체 리팩토링

@Service
class AuthTokenService(
    private val tokenProviderPort: TokenProviderPort,
    private val authTokenPersistencePort: AuthTokenPersistencePort,
) : ExtractTokenUseCase,
    ReissueAuthTokenUseCase,
    LogoutUseCase {
    override fun extractToken(accessToken: String): Long = tokenProviderPort.extractUserId(accessToken)

    override fun reissueAuthToken(refreshToken: String): TokenPairResult {
        val tokenId = tokenProviderPort.extractTokenId(refreshToken)
        val authToken = authTokenPersistencePort.getWithDeleteByTokenId(tokenId)
        val rotateAuthToken = authTokenPersistencePort.saveAuthToken(authToken.rotateAuthToken())
        return TokenPairResult(
            accessToken = tokenProviderPort.generateAccessToken(rotateAuthToken.userId),
            refreshToken = tokenProviderPort.generateRefreshToken(rotateAuthToken.id),
        )
    }

    override fun logout(refreshToken: String) {
        val tokenId = tokenProviderPort.extractTokenId(refreshToken)
        authTokenPersistencePort.deleteByTokenId(tokenId)
    }
}
