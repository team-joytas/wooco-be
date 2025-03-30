package kr.wooco.woocobe.core.user.domain.vo

data class SocialUser(
    val socialId: String,
    val socialType: SocialType,
) {
    init {
        require(socialId.isNotBlank()) { "소셜 식별자는 빈값이 될 수 없습니다." }
    }
}
