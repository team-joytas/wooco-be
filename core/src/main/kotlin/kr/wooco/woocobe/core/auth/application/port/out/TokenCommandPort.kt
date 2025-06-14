package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.Token

interface TokenCommandPort {
    fun saveAuthToken(token: Token): Token

    fun deleteAuthToken(token: Token)
}
