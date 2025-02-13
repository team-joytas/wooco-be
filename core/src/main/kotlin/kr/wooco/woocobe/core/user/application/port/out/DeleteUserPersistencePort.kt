package kr.wooco.woocobe.core.user.application.port.out

interface DeleteUserPersistencePort {
    fun deleteByUserId(userId: Long)
}
