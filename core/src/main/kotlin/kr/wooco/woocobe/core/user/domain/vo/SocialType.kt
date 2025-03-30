package kr.wooco.woocobe.core.user.domain.vo

enum class SocialType {
    KAKAO,
    ;

    companion object {
        operator fun invoke(value: String) = valueOf(value.uppercase())
    }
}
