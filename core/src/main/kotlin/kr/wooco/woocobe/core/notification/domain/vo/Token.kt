package kr.wooco.woocobe.core.notification.domain.vo

@JvmInline
value class Token(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "응애~" }
    }
}
