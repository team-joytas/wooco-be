package kr.wooco.woocobe.api.user

import kr.wooco.woocobe.api.common.security.LoginRequired
import kr.wooco.woocobe.api.user.request.UpdateUserRequest
import kr.wooco.woocobe.api.user.response.UserDetailResponse
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.UpdateUserProfileUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.WithdrawUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
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
    private val withdrawUserUseCase: WithdrawUserUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
) : UserApi {
    @LoginRequired
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

    @LoginRequired
    @PatchMapping("/profile")
    override fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId)
        updateUserProfileUseCase.updateUserProfile(command)
        return ResponseEntity.ok().build()
    }

    @LoginRequired
    @DeleteMapping("/withdraw")
    override fun withdrawUser(
        @CookieValue(SOCIAL_TOKEN) socialToken: String,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Unit> {
        val command = WithdrawUserUseCase.Command(userId = userId, socialToken = socialToken)
        withdrawUserUseCase.withdrawUser(command)
        return ResponseEntity.noContent().build()
    }

    companion object {
        private const val SOCIAL_TOKEN = "social-token"
    }
}
