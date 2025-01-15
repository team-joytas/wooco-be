package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdateUserInput(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
)

@Service
class UpdateUserUseCase(
    private val userStorageGateway: UserStorageGateway,
) : UseCase<UpdateUserInput, Unit> {
    @Transactional
    override fun execute(input: UpdateUserInput) {
        val user = userStorageGateway.getByUserId(userId = input.userId)

        user.update(name = input.name, profileUrl = input.profileUrl, description = input.description)
        userStorageGateway.save(user)
    }
}
