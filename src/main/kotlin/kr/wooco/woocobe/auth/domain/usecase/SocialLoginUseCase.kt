package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class SocialLoginInput(
    val socialType: String,
    val socialToken: String,
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
        val socialAuthInfo = socialAuthClientGateway.getSocialAuthInfo(input.socialToken)

        val authUser = authUserStorageGateway.getBySocialIdAndSocialType(
            socialId = socialAuthInfo.socialId,
            socialType = socialAuthInfo.socialType,
        ) ?: run {
            val user = userStorageGateway.save(User.register())
            authUserStorageGateway.save(
                AuthUser.register(
                    userId = user.id,
                    socialAuth = socialAuthInfo,
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
