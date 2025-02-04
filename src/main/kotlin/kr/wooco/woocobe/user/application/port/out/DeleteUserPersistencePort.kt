package kr.wooco.woocobe.user.application.port.out

interface DeleteUserPersistencePort {
    fun deleteByUserId(userId: Long)
}
