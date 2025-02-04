package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.entity.User
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

        return GetUserOutput(
            user = user,
        )
    }
}
