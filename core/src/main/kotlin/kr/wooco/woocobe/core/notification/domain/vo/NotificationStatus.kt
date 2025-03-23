package kr.wooco.woocobe.core.notification.domain.vo

enum class NotificationStatus {
    ACTIVE,
    DELETED,
    ;

    companion object {
        operator fun invoke(value: String) = NotificationStatus.valueOf(value)
    }
}
