package kr.wooco.woocobe.core.notification.domain.vo

enum class NotificationType {
    COURSE_COMMENT_CREATED,
    PLAN_SHARE_REQUEST,
    PLACE_REVIEW_REQUEST,
    SYSTEM,
    ;

    companion object {
        operator fun invoke(value: String) = NotificationType.valueOf(value)
    }
}
