package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.entity.User
import org.springframework.stereotype.Service

data class GetAllUserInput(
    val userIds: List<Long>,
)

data class GetAllUserOutput(
    val users: List<User>,
)

@Service
class GetAllUserUseCase(
    private val userStorageGateway: UserStorageGateway,
) : UseCase<GetAllUserInput, GetAllUserOutput> {
    override fun execute(input: GetAllUserInput): GetAllUserOutput {
        val users = userStorageGateway.getAllByUserIds(input.userIds)

        return GetAllUserOutput(
            users = users,
        )
    }
}
