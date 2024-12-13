package kr.wooco.woocobe.auth.domain.model

enum class SocialAuthType {
    KAKAO,
    ;

    companion object {
        fun from(value: String): SocialAuthType =
            entries.find { it.name.equals(value, ignoreCase = true) }
                ?: throw RuntimeException()
    }
}
