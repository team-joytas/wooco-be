package kr.wooco.woocobe.auth.domain.vo

enum class SocialType {
    KAKAO,
    ;

    companion object {
        operator fun invoke(value: String) = valueOf(value)
    }
}
