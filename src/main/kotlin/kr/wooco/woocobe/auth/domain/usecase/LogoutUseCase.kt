package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.common.domain.usecase.UseCase
import org.springframework.stereotype.Service

data class LogoutInput(
    val userId: Long,
    val refreshToken: String,
)

@Service
class LogoutUseCase(
    private val tokenProviderGateway: TokenProviderGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : UseCase<LogoutInput, Unit> {
    override fun execute(input: LogoutInput) {
        val tokenId = tokenProviderGateway.extractTokenId(input.refreshToken)
        authTokenStorageGateway.deleteByTokenId(tokenId)
    }
}
