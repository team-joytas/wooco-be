package kr.wooco.woocobe.auth.domain.model

enum class SocialType {
    KAKAO,
    ;

    companion object {
        fun from(value: String): SocialType =
            entries.find { it.name == value.uppercase() }
                ?: throw RuntimeException()
    }
}
