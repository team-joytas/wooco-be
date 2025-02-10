package kr.wooco.woocobe.auth.application.port.out

import kr.wooco.woocobe.auth.domain.entity.AuthCode

interface AuthCodePersistencePort {
    fun saveAuthCode(authCode: AuthCode): AuthCode

    fun getWithDeleteByAuthCodeId(authCodeId: String): AuthCode
}
