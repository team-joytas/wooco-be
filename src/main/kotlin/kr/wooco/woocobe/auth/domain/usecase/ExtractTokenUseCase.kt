package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.common.domain.usecase.UseCase
import org.springframework.stereotype.Service

data class ExtractTokenInput(
    val accessToken: String,
)

data class ExtractTokenOutput(
    val userId: Long,
)

@Service
class ExtractTokenUseCase(
    private val tokenProviderGateway: TokenProviderGateway,
) : UseCase<ExtractTokenInput, ExtractTokenOutput> {
    override fun execute(input: ExtractTokenInput): ExtractTokenOutput {
        val userId = tokenProviderGateway.extractUserId(input.accessToken)

        return ExtractTokenOutput(
            userId = userId,
        )
    }
}
