package kr.wooco.woocobe.core.auth.application.port.out

interface DeleteAuthUserPersistencePort {
    fun deleteByUserId(userId: Long)
}
