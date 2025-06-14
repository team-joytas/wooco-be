package kr.wooco.woocobe.core.notification.domain.vo

enum class NotificationReadStatus {
    UNREAD,
    READ,
    ;

    companion object {
        operator fun invoke(value: String) = NotificationReadStatus.valueOf(value)
    }
}
