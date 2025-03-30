package kr.wooco.woocobe.core.auth.application.service

import kr.wooco.woocobe.core.auth.application.port.`in`.DeleteTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.IssueTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.ReissueTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.out.TokenCommandPort
import kr.wooco.woocobe.core.auth.application.port.out.TokenQueryPort
import kr.wooco.woocobe.core.auth.domain.entity.Token
import org.springframework.stereotype.Service

@Service
internal class AuthCommandService(
    private val tokenQueryPort: TokenQueryPort,
    private val tokenCommandPort: TokenCommandPort,
) : IssueTokenUseCase,
    DeleteTokenUseCase,
    ReissueTokenUseCase {
    override fun issueToken(command: IssueTokenUseCase.Command): Long {
        val token = Token.create(command.userId)
        return tokenCommandPort.saveAuthToken(token).id
    }

    override fun deleteToken(command: DeleteTokenUseCase.Command) {
        val token = tokenQueryPort.getAuthTokenByTokenId(command.tokenId)
        token.validateUserId(command.userId)
        tokenCommandPort.deleteAuthToken(token)
    }

    override fun reissueToken(command: ReissueTokenUseCase.Command): Long {
        val token = tokenQueryPort.getAuthTokenByTokenId(command.tokenId)
        token.validateUserId(command.userId)
        tokenCommandPort.deleteAuthToken(token)

        val rotateToken = Token.create(token.userId)
        return tokenCommandPort.saveAuthToken(rotateToken).id
    }
}
