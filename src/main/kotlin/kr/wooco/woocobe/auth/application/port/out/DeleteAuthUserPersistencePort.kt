package kr.wooco.woocobe.auth.application.port.out

interface DeleteAuthUserPersistencePort {
    fun deleteByUserId(userId: Long)
}
