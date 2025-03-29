package kr.wooco.woocobe.core.user.application.service

import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.results.UserResult
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserQueryService(
    private val userQueryPort: UserQueryPort,
) : ReadUserUseCase {
    @Transactional(readOnly = true)
    override fun readUser(query: ReadUserUseCase.Query): UserResult {
        val user = userQueryPort.getByUserId(query.userId)
        return UserResult.fromUser(user)
    }
}
