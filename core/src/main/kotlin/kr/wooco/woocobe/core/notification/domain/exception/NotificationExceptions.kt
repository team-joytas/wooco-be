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

data object InvalidNotificationOwnerException : BaseNotificationException(
    code = "INVALID_NOTIFICATION_OWNER_EXCEPTION",
    message = "유효하지 않은 알림 수신자입니다.",
) {
    private fun readResolve(): Any = InvalidNotificationOwnerException
}

data object AlreadyDeletedNotificationException : BaseNotificationException(
    code = "ALREADY_DELETED_NOTIFICATION_EXCEPTION",
    message = "이미 삭제된 알림입니다.",
) {
    private fun readResolve(): Any = AlreadyDeletedNotificationException
}

data object NotExistsDeviceTokenException : BaseNotificationException(
    code = "NOT_EXISTS_DEVICE_TOKEN_EXCEPTION",
    message = "존재하지 않는 디바이스 토큰입니다.",
) {
    private fun readResolve(): Any = NotExistsDeviceTokenException
}

data object InvalidDeviceTokenOwnerException : BaseNotificationException(
    code = "INVALID_DEVICE_TOKEN_OWNER_EXCEPTION",
    message = "유효하지 않은 디바이스 토큰 소유자입니다.",
) {
    private fun readResolve(): Any = InvalidDeviceTokenOwnerException
}

data object AlreadyDeletedDeviceTokenException : BaseNotificationException(
    code = "ALREADY_DELETED_DEVICE_TOKEN_EXCEPTION",
    message = "이미 삭제된 디바이스 토큰입니다.",
) {
    private fun readResolve(): Any = AlreadyDeletedDeviceTokenException
}
