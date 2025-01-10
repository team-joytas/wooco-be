package kr.wooco.woocobe.user.ui.web.controller

import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import kr.wooco.woocobe.user.domain.usecase.UpdateUserInput
import kr.wooco.woocobe.user.domain.usecase.UpdateUserUseCase
import kr.wooco.woocobe.user.ui.web.controller.request.UpdateUserProfileRequest
import kr.wooco.woocobe.user.ui.web.controller.response.GetCurrentUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) {
    @GetMapping("/me")
    fun getCurrentUser(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<GetCurrentUserResponse> {
        val result = getUserUseCase.execute(
            GetUserInput(
                userId = userId,
            ),
        )
        val response = GetCurrentUserResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserProfileRequest,
    ): ResponseEntity<Unit> {
        updateUserUseCase.execute(
            UpdateUserInput(
                userId = userId,
                name = request.name,
                profileUrl = request.profileUrl,
            ),
        )
        return ResponseEntity.ok().build()
    }
}
