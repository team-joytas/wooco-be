package kr.wooco.woocobe.user.domain.vo

enum class UserStatus {
    ONBOARDING,
    ACTIVE,
    INACTIVE,
    ;

    companion object {
        operator fun invoke(value: String): UserStatus = valueOf(value)
    }
}
