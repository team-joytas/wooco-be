package kr.wooco.woocobe.auth.domain.model

import kr.wooco.woocobe.auth.domain.exception.UnSupportSocialTypeException

enum class SocialType {
    KAKAO,
    ;

    companion object {
        fun from(value: String): SocialType =
            entries.find { it.name == value.uppercase() }
                ?: throw UnSupportSocialTypeException
    }
}
