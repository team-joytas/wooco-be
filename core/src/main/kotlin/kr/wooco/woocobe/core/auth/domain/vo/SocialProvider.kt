package kr.wooco.woocobe.core.auth.domain.vo

enum class OAuthProvider {
    KAKAO,
    ;

    companion object {
        operator fun invoke(value: String) = valueOf(value)
    }
}
