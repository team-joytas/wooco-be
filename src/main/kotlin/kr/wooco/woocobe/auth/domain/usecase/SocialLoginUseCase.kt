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
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
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

@Service
class SocialLoginUseCase(
    private val userStorageGateway: UserStorageGateway,
    private val pkceStorageGateway: PkceStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
    private val socialAuthClientGateway: SocialAuthClientGateway,
) : UseCase<SocialLoginInput, SocialLoginOutput> {
    @Transactional
    override fun execute(input: SocialLoginInput): SocialLoginOutput {
        val pkce = pkceStorageGateway.getWithDeleteByChallenge(input.challenge)
            ?: throw RuntimeException()

        val socialType = SocialType.from(input.socialType)
        val socialAuth = socialAuthClientGateway.fetchSocialAuth(input.authCode, socialType, pkce)

        val authUser = authUserStorageGateway.getOrNullBySocialIdAndSocialType(
            socialId = socialAuth.socialId,
            socialType = socialAuth.socialType,
        ) ?: run {
            val user = userStorageGateway.save(User.register())
            authUserStorageGateway.save(
                AuthUser.register(
                    userId = user.id,
                    socialAuth = socialAuth,
                ),
            )
        }

        val authToken = AuthToken
            .register(userId = authUser.userId)
            .run(authTokenStorageGateway::save)

        return SocialLoginOutput(
            accessToken = tokenProviderGateway.generateAccessToken(authToken.userId),
            refreshToken = tokenProviderGateway.generateRefreshToken(authToken.id),
        )
    }
}
