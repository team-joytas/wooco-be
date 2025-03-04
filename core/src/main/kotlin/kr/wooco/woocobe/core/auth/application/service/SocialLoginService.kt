package kr.wooco.woocobe.core.auth.application.service

import kr.wooco.woocobe.core.auth.application.port.`in`.ReadSocialLoginUrlUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.SocialLoginUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.WithdrawUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.results.SocialLoginUrlResult
import kr.wooco.woocobe.core.auth.application.port.`in`.results.TokenPairResult
import kr.wooco.woocobe.core.auth.application.port.out.AuthCodePersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.AuthTokenPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.DeleteAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.LoadAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.SaveAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.SocialAuthClientPort
import kr.wooco.woocobe.core.auth.application.port.out.TokenProviderPort
import kr.wooco.woocobe.core.auth.domain.entity.AuthCode
import kr.wooco.woocobe.core.auth.domain.entity.AuthToken
import kr.wooco.woocobe.core.auth.domain.entity.AuthUser
import kr.wooco.woocobe.core.user.application.port.out.DeleteUserPersistencePort
import kr.wooco.woocobe.core.user.application.port.out.SaveUserPersistencePort
import kr.wooco.woocobe.core.user.domain.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// TODO: 로직 전체 리팩토링

@Service
class SocialLoginService(
    private val tokenProviderPort: TokenProviderPort,
    private val socialAuthClientPort: SocialAuthClientPort,
    private val authCodePersistencePort: AuthCodePersistencePort,
    private val authTokenPersistencePort: AuthTokenPersistencePort,
    private val saveUserPersistencePort: SaveUserPersistencePort,
    private val deleteUserPersistencePort: DeleteUserPersistencePort,
    private val loadAuthUserPersistencePort: LoadAuthUserPersistencePort,
    private val saveAuthUserPersistencePort: SaveAuthUserPersistencePort,
    private val deleteAuthUserPersistencePort: DeleteAuthUserPersistencePort,
) : ReadSocialLoginUrlUseCase,
    SocialLoginUseCase,
    WithdrawUseCase {
    override fun readSocialLoginUrl(socialType: String): SocialLoginUrlResult {
        val authCode = authCodePersistencePort.saveAuthCode(AuthCode.create())
        val loginUrl = socialAuthClientPort.getSocialLoginUrl(socialType, authCode.challenge)
        return SocialLoginUrlResult(
            loginUrl = loginUrl,
            authCodeId = authCode.id,
        )
    }

    @Transactional
    override fun socialLogin(
        code: String,
        authCodeId: String,
        socialType: String,
    ): TokenPairResult {
        val authCode = authCodePersistencePort.getWithDeleteByAuthCodeId(authCodeId)
        val socialInfo = socialAuthClientPort.fetchSocialInfo(code, socialType, authCode.verifier, authCode.challenge)
        val authUser = loadAuthUserPersistencePort.getOrNullBySocialIdAndSocialType(socialInfo.socialId, socialType)
            ?: run {
                val user = saveUserPersistencePort.saveUser(User.createDefault())
                saveAuthUserPersistencePort.saveAuthUser(AuthUser.create(user.id, socialInfo))
            }
        val authToken = AuthToken.create(authUser.userId)
        return TokenPairResult(
            accessToken = tokenProviderPort.generateAccessToken(authToken.userId),
            refreshToken = tokenProviderPort.generateRefreshToken(authToken.id),
        )
    }

    @Transactional
    override fun withdraw(
        userId: Long,
        refreshToken: String,
    ) {
        val tokenId = tokenProviderPort.extractTokenId(refreshToken)
        authTokenPersistencePort.deleteByTokenId(tokenId)
        deleteUserPersistencePort.deleteByUserId(userId)
        deleteAuthUserPersistencePort.deleteByUserId(userId)
    }
}
