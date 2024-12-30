package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
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
    val socialType: String,
    val authCode: String,
)

data class SocialLoginOutput(
    val accessToken: String,
    val refreshToken: String,
)

@Service
class SocialLoginUseCase(
    private val userStorageGateway: UserStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
    private val socialAuthClientGateway: SocialAuthClientGateway,
) : UseCase<SocialLoginInput, SocialLoginOutput> {
    @Transactional
    override fun execute(input: SocialLoginInput): SocialLoginOutput {
        val socialType = SocialType.valueOf(input.socialType)
        val socialAuth = socialAuthClientGateway.fetchSocialAuth(input.authCode, socialType)

        val authUser = authUserStorageGateway.getBySocialIdAndSocialType(
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
