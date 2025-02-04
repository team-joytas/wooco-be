package kr.wooco.woocobe.auth.domain.usecase

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.application.port.out.DeleteUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class WithdrawInput(
    val userId: Long,
    val refreshToken: String,
)

@Service
class WithdrawUseCase(
    private val deleteUserPersistencePort: DeleteUserPersistencePort,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : UseCase<WithdrawInput, Unit> {
    @Transactional
    override fun execute(input: WithdrawInput) {
        val tokenId = tokenProviderGateway.extractTokenId(input.refreshToken)

        deleteUserPersistencePort.deleteByUserId(input.userId)
        authUserStorageGateway.deleteByUserId(input.userId)
        authTokenStorageGateway.deleteByTokenId(tokenId)
    }
}
