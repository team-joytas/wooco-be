package kr.wooco.woocobe.user.application.service

import kr.wooco.woocobe.user.application.port.`in`.UpdateUserProfileUseCase
import kr.wooco.woocobe.user.application.port.`in`.WithdrawUserUseCase
import kr.wooco.woocobe.user.application.port.out.DeleteUserPersistencePort
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import kr.wooco.woocobe.user.application.port.out.SaveUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserCommandService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
    private val saveUserPersistencePort: SaveUserPersistencePort,
    private val deleteUserPersistencePort: DeleteUserPersistencePort,
) : UpdateUserProfileUseCase,
    WithdrawUserUseCase {
    @Transactional
    override fun updateUserProfile(command: UpdateUserProfileUseCase.Command) {
        val user = loadUserPersistencePort.getByUserId(command.userId)
        user.updateProfile(
            name = command.name,
            profileUrl = command.profileUrl,
            description = command.description,
        )
        saveUserPersistencePort.saveUser(user)
    }

    @Transactional
    override fun withdrawUser(command: WithdrawUserUseCase.Command) {
        deleteUserPersistencePort.deleteByUserId(command.userId)
    }
}
