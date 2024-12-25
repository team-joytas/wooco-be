package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class WithdrawInput(
    val userId: Long,
    val refreshToken: String,
)

@Service
class WithdrawUseCase(
    private val userStorageGateway: UserStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : UseCase<WithdrawInput, Unit> {
    @Transactional
    override fun execute(input: WithdrawInput) {
        val tokenId = tokenProviderGateway.extractTokenId(input.refreshToken)

        val authToken = authTokenStorageGateway.getByTokenId(tokenId)
            ?: throw RuntimeException()

        when {
            authToken.isMatchUserId(input.userId).not() -> throw RuntimeException()
        }

        userStorageGateway.deleteByUserId(input.userId)
        authUserStorageGateway.deleteByUserId(input.userId)
        authTokenStorageGateway.deleteByTokenId(tokenId)
    }
}
