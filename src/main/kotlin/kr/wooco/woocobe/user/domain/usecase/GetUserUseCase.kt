// FIXME: 삭제

package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
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
    private val loadUserPersistencePort: LoadUserPersistencePort,
) : UseCase<GetUserInput, GetUserOutput> {
    @Deprecated(message = "outbound port 를 통해 필요한 데이터를 가져오는 방식으로 수정해주세요.")
    override fun execute(input: GetUserInput): GetUserOutput {
        val user = loadUserPersistencePort.getByUserId(input.userId)

        return GetUserOutput(
            user = user,
        )
    }
}
