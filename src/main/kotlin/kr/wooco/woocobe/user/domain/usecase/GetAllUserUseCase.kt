// FIXME: 삭제

package kr.wooco.woocobe.user.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
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
    private val loadUserPersistencePort: LoadUserPersistencePort,
) : UseCase<GetAllUserInput, GetAllUserOutput> {
    @Deprecated(message = "outbound port 를 통해 필요한 데이터를 가져오는 방식으로 수정해주세요.")
    override fun execute(input: GetAllUserInput): GetAllUserOutput {
        val users = loadUserPersistencePort.getAllByUserIds(input.userIds)

        return GetAllUserOutput(
            users = users,
        )
    }
}
