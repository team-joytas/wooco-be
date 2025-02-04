package kr.wooco.woocobe.user.adapter.`in`.web

import kr.wooco.woocobe.user.adapter.`in`.web.request.UpdateUserRequest
import kr.wooco.woocobe.user.adapter.`in`.web.response.UserDetailResponse
import kr.wooco.woocobe.user.application.port.`in`.ReadUserUseCase
import kr.wooco.woocobe.user.application.port.`in`.UpdateUserProfileUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val readUserUseCase: ReadUserUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
) : UserApi {
    @GetMapping("/me")
    override fun getCurrentUser(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<UserDetailResponse> {
        val query = ReadUserUseCase.Query(userId)
        val results = readUserUseCase.readUser(query)
        return ResponseEntity.ok(UserDetailResponse.from(results))
    }

    @GetMapping("/{userId}")
    override fun getUser(
        @PathVariable userId: Long,
    ): ResponseEntity<UserDetailResponse> {
        val query = ReadUserUseCase.Query(userId)
        val results = readUserUseCase.readUser(query)
        return ResponseEntity.ok(UserDetailResponse.from(results))
    }

    @PatchMapping("/profile")
    override fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId)
        updateUserProfileUseCase.updateUserProfile(command)
        return ResponseEntity.ok().build()
    }
}
