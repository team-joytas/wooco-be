package kr.wooco.woocobe.user.application.service

import kr.wooco.woocobe.user.application.port.`in`.ReadUserUseCase
import kr.wooco.woocobe.user.application.port.`in`.results.UserResult
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserQueryService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
) : ReadUserUseCase {
    @Transactional(readOnly = true)
    override fun readUser(query: ReadUserUseCase.Query): UserResult {
        val user = loadUserPersistencePort.getByUserId(query.userId)
        return UserResult.fromUser(user)
    }
}
