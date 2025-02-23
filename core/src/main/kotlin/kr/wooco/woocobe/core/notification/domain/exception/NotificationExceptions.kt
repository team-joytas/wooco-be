package kr.wooco.woocobe.core.notification.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseNotificationException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsNotificationException : BaseNotificationException(
    code = "NOT_EXISTS_NOTIFICATION_EXCEPTION",
    message = "존재하지 않는 알림입니다.",
) {
    private fun readResolve(): Any = NotExistsNotificationException
}

data object ExpiredNotificationException : BaseNotificationException(
    code = "EXPIRED_NOTIFICATION_EXCEPTION",
    message = "유효기간이 만료된 알림입니다.",
) {
    private fun readResolve(): Any = ExpiredNotificationException
}

data object InvalidNotificationTypeException : BaseNotificationException(
    code = "INVALID_NOTIFICATION_TYPE_EXCEPTION",
    message = "유효하지 않은 알림 타입입니다.",
) {
    private fun readResolve(): Any = InvalidNotificationTypeException
}
