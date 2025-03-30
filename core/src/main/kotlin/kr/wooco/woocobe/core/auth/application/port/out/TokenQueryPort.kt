package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.Token

interface TokenQueryPort {
    fun getAuthTokenByTokenId(tokenId: Long): Token
}
