package kr.wooco.woocobe.user.ui.web.facade

import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import kr.wooco.woocobe.user.domain.usecase.UpdateUserUseCase
import kr.wooco.woocobe.user.ui.web.controller.request.UpdateUserRequest
import kr.wooco.woocobe.user.ui.web.controller.response.UserDetailResponse
import org.springframework.stereotype.Service

@Service
class UserFacadeService(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) {
    fun getUser(userId: Long): UserDetailResponse {
        val getUserResult = getUserUseCase.execute(GetUserInput(userId))
        return UserDetailResponse.from(user = getUserResult.user)
    }

    fun updateUser(
        userId: Long,
        request: UpdateUserRequest,
    ) = updateUserUseCase.execute(request.toCommand(userId))
}
