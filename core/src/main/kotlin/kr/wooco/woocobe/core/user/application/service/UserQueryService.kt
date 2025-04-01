package kr.wooco.woocobe.core.user.application.service

import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserInfoUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.results.UserInfoResult
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
internal class UserQueryService(
    private val userQueryPort: UserQueryPort,
) : ReadUserInfoUseCase {
    override fun readUserInfo(query: ReadUserInfoUseCase.Query): UserInfoResult {
        val user = userQueryPort.getByUserId(query.userId)
        return UserInfoResult.fromUser(user)
    }
}
