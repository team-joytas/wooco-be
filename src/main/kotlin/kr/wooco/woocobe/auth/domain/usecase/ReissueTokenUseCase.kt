package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.common.domain.usecase.UseCase
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
        val tokenId = tokenProviderGateway.extractTokenId(input.refreshToken)

        val authToken = authTokenStorageGateway.getWithDeleteByTokenId(tokenId)
            ?: throw RuntimeException()

        val newAuthToken = AuthToken
            .register(userId = authToken.userId)
            .run(authTokenStorageGateway::save)

        return ReissueTokenOutput(
            accessToken = tokenProviderGateway.generateAccessToken(newAuthToken.userId),
            refreshToken = tokenProviderGateway.generateRefreshToken(newAuthToken.id),
        )
    }
}
