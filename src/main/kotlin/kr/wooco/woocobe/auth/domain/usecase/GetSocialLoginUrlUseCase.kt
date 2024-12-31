package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.PkceStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.model.Pkce
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.common.domain.usecase.UseCase
import org.springframework.stereotype.Service

data class GetSocialLoginUrlInput(
    val socialType: String,
)

data class GetSocialLoginUrlOutput(
    val loginUrl: String,
    val challenge: String,
)

@Service
class GetSocialLoginUrlUseCase(
    private val pkceStorageGateway: PkceStorageGateway,
    private val socialAuthClientGateway: SocialAuthClientGateway,
) : UseCase<GetSocialLoginUrlInput, GetSocialLoginUrlOutput> {
    override fun execute(input: GetSocialLoginUrlInput): GetSocialLoginUrlOutput {
        val socialType = SocialType.from(input.socialType)

        val pkce = Pkce.register().run(pkceStorageGateway::save)

        return GetSocialLoginUrlOutput(
            loginUrl = socialAuthClientGateway.getSocialLoginUrl(socialType, pkce.challenge),
            challenge = pkce.challenge,
        )
    }
}
