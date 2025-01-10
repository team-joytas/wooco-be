package kr.wooco.woocobe.user.ui.web.facade

import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import kr.wooco.woocobe.user.domain.usecase.UpdateUserInput
import kr.wooco.woocobe.user.domain.usecase.UpdateUserUseCase
import kr.wooco.woocobe.user.ui.web.controller.request.UpdateUserRequest
import kr.wooco.woocobe.user.ui.web.controller.response.GetCurrentUserResponse
import org.springframework.stereotype.Service

@Service
class UserFacadeService(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) {
    fun getCurrentUser(userId: Long): GetCurrentUserResponse {
        val getUserResult = getUserUseCase.execute(
            GetUserInput(userId = userId),
        )
        return GetCurrentUserResponse.from(getUserResult)
    }

    fun updateUser(
        userId: Long,
        request: UpdateUserRequest,
    ) {
        updateUserUseCase.execute(
            UpdateUserInput(
                userId = userId,
                name = request.name,
                profileUrl = request.profileUrl,
            ),
        )
    }
}
