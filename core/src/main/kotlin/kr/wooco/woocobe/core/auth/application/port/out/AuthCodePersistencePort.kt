package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.AuthCode

interface AuthCodePersistencePort {
    fun saveAuthCode(authCode: AuthCode): AuthCode

    fun getWithDeleteByAuthCodeId(authCodeId: String): AuthCode
}
