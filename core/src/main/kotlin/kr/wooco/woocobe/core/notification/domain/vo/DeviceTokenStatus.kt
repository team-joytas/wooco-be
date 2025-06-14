package kr.wooco.woocobe.core.notification.domain.vo

enum class DeviceTokenStatus {
    ACTIVE,
    DELETED,
    ;

    companion object {
        operator fun invoke(value: String) = DeviceTokenStatus.valueOf(value)
    }
}
