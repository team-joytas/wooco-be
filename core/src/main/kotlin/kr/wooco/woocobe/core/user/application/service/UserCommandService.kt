package kr.wooco.woocobe.core.user.application.service

import kr.wooco.woocobe.core.user.application.port.`in`.SocialLoginUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.UpdateUserProfileUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.WithdrawUserUseCase
import kr.wooco.woocobe.core.user.application.port.out.SocialUserClientPort
import kr.wooco.woocobe.core.user.application.port.out.UserCommandPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.vo.SocialType
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import kr.wooco.woocobe.core.user.domain.vo.UserProfile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserCommandService(
    private val userQueryPort: UserQueryPort,
    private val userCommandPort: UserCommandPort,
    private val socialUserClientPort: SocialUserClientPort,
) : SocialLoginUseCase,
    UpdateUserProfileUseCase,
    WithdrawUserUseCase {
    @Transactional
    override fun socialLogin(command: SocialLoginUseCase.Command): Long {
        val socialUser = SocialUser(
            socialId = command.socialId,
            socialType = SocialType(command.socialType),
        )
        val user = userQueryPort.getOrNullBySocialUser(socialUser)?.login()
            ?: User.create(socialUser)
        return userCommandPort.saveUser(user).id
    }

    @Transactional
    override fun updateUserProfile(command: UpdateUserProfileUseCase.Command) {
        val user = userQueryPort.getByUserId(command.userId)
        val userProfile = UserProfile(
            name = command.name,
            profileUrl = command.profileUrl,
            description = command.description,
        )
        val updateUser = user.updateProfile(userProfile)
        userCommandPort.saveUser(updateUser)
    }

    override fun withdrawUser(command: WithdrawUserUseCase.Command) {
        val user = userQueryPort.getByUserId(command.userId)
        socialUserClientPort.revokeSocialUser(
            socialToken = command.socialToken,
            socialUser = user.socialUser,
        )
        val withdrawUser = user.withdraw()
        userCommandPort.saveUser(withdrawUser)
    }
}
