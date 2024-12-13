package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.common.domain.UseCase
import org.springframework.stereotype.Service

data class ReissueTokenInput(
    val refreshToken: String,
)

data class ReissueTokenOutput(
    val accessToken: String,
    val refreshToken: String,
)

@Service
class ReissueTokenUseCase(
    private val tokenProviderGateway: TokenProviderGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : UseCase<ReissueTokenInput, ReissueTokenOutput> {
    override fun execute(input: ReissueTokenInput): ReissueTokenOutput {
        val tokenId = tokenProviderGateway.extractRefreshToken(input.refreshToken)

        val authToken = authTokenStorageGateway.getByTokenId(tokenId)?.let {
            it.regenerateId()
            authTokenStorageGateway.save(it)
        } ?: throw RuntimeException()

        return ReissueTokenOutput(
            accessToken = tokenProviderGateway.generateAccessToken(authToken.userId),
            refreshToken = tokenProviderGateway.generateRefreshToken(authToken.tokenId),
        )
    }
}
