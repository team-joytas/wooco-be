package kr.wooco.woocobe.core.user.domain.vo

enum class SocialProvider {
    KAKAO,
    ;

    companion object {
        operator fun invoke(value: String) = valueOf(value)
    }
}
