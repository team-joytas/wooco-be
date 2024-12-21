package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import org.springframework.stereotype.Service

data class GetUserInput(
    val userId: Long,
)

data class GetUserOutput(
    val user: User,
)

@Service
class GetUserUseCase(
    private val userStorageGateway: UserStorageGateway,
) : UseCase<GetUserInput, GetUserOutput> {
    override fun execute(input: GetUserInput): GetUserOutput {
        val user = userStorageGateway.getByUserId(userId = input.userId)
            ?: throw RuntimeException()

        return GetUserOutput(
            user = user,
        )
    }
}
