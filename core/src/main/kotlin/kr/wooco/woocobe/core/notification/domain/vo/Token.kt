package kr.wooco.woocobe.core.notification.domain.vo

@JvmInline
value class Token(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "토큰은 빈 값일 수 없습니다." }
    }
}
