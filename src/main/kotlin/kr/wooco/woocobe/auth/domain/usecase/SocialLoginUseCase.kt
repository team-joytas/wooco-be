package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.PkceStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.application.port.out.SaveUserPersistencePort
import kr.wooco.woocobe.user.domain.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class SocialLoginInput(
    val authCode: String,
    val socialType: String,
    val challenge: String,
)

data class SocialLoginOutput(
    val accessToken: String,
    val refreshToken: String,
)

// TODO 책임과 역할이 너무 많다 -> 분리 예정
@Service
class SocialLoginUseCase(
    private val saveUserPersistencePort: SaveUserPersistencePort,
    private val pkceStorageGateway: PkceStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
    private val socialAuthClientGateway: SocialAuthClientGateway,
) : UseCase<SocialLoginInput, SocialLoginOutput> {
    @Transactional
    override fun execute(input: SocialLoginInput): SocialLoginOutput {
        val pkce = pkceStorageGateway.getWithDeleteByChallenge(input.challenge)

        val socialType = SocialType.from(input.socialType)
        val socialAuth = socialAuthClientGateway.fetchSocialAuth(input.authCode, socialType, pkce)

        val authUser = authUserStorageGateway.getOrNullBySocialIdAndSocialType(
            socialId = socialAuth.socialId,
            socialType = socialAuth.socialType,
        ) ?: run {
            val user = saveUserPersistencePort.saveUser(User.createDefault())
            authUserStorageGateway.save(
                AuthUser.register(
                    userId = user.id,
                    socialAuth = socialAuth,
                ),
            )
        }

        val authToken = AuthToken(userId = authUser.userId)
        authTokenStorageGateway.save(authToken)

        return SocialLoginOutput(
            accessToken = tokenProviderGateway.generateAccessToken(authToken.userId),
            refreshToken = tokenProviderGateway.generateRefreshToken(authToken.tokenId),
        )
    }
}
