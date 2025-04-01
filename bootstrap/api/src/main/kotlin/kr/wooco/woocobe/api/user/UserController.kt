package kr.wooco.woocobe.api.user

import kr.wooco.woocobe.api.user.request.UpdateUserRequest
import kr.wooco.woocobe.api.user.response.UserDetailsResponse
import kr.wooco.woocobe.api.user.response.UserInfoResponse
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserDetailsUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserInfoUseCase
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
    private val readUserInfoUseCase: ReadUserInfoUseCase,
    private val withdrawUserUseCase: WithdrawUserUseCase,
    private val readUserDetailsUseCase: ReadUserDetailsUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
) : UserApi {
    @GetMapping("/me")
    override fun getCurrentUserInfo(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<UserInfoResponse> {
        val query = ReadUserInfoUseCase.Query(userId)
        val results = readUserInfoUseCase.readUserInfo(query)
        return ResponseEntity.ok(UserInfoResponse.from(results))
    }

    @GetMapping("/{userId}")
    override fun getUserInfo(
        @PathVariable userId: Long,
    ): ResponseEntity<UserInfoResponse> {
        val query = ReadUserInfoUseCase.Query(userId)
        val results = readUserInfoUseCase.readUserInfo(query)
        return ResponseEntity.ok(UserInfoResponse.from(results))
    }

    @GetMapping("/{userId}/details")
    override fun getUserDetails(
        @PathVariable userId: Long,
    ): ResponseEntity<UserDetailsResponse> {
        val query = ReadUserDetailsUseCase.Query(userId)
        val results = readUserDetailsUseCase.readUserDetails(query)
        return ResponseEntity.ok(UserDetailsResponse.from(results))
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
