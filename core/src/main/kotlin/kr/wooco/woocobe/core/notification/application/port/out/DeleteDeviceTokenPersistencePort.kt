package kr.wooco.woocobe.core.notification.application.port.out

interface DeleteDeviceTokenPersistencePort {
    fun deleteDeviceToKen(token: String)
}
