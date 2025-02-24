package kr.wooco.woocobe.core.user.domain.vo

// TODO: value class 추가
data class UserProfile(
    val name: String,
    val profileUrl: String,
    val description: String,
) {
    init {
        if (name.isNotBlank()) {
            require(name.length in 2..10) { "이름은 2글자에서 10글자 내로 작성해야합니다." }
        }

        if (profileUrl.isNotBlank()) {
            require(profileUrl.startsWith(URL_PREFIX)) { "프로필 이미지는 URL 형식이어야 합니다." }
        }

        if (description.isNotBlank()) {
            require(description.length in 1..20) { "소개글은 1글자에서 20글자 내로 작성해야합니다." }
        }
    }

    companion object {
        private const val URL_PREFIX = "https://"
    }
}
