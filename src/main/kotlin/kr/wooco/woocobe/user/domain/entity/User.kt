package kr.wooco.woocobe.user.domain.entity

class User(
    val id: Long,
    var profile: UserProfile,
) {
    constructor() : this(
        id = 0L,
        profile = UserProfile(),
    )

    @JvmRecord
    data class UserProfile(
        val name: String,
        val profileUrl: String,
        val description: String,
    ) {
        constructor() : this(
            name = "",
            profileUrl = "",
            description = "",
        )

        init {
            validate()
        }

        private fun validate() {
            if (this == DEFAULT) return

            require(name.length in 2..10) { "이름은 2글자에서 10글자 내로 작성해야합니다." }
            require(profileUrl.startsWith(URL_PREFIX)) { "프로필 이미지는 URL 형식이어야 합니다." }
            require(description.length in 1..20) { "소개글은 1글자에서 20글자 내로 작성해야합니다." }
        }

        companion object {
            private val DEFAULT = UserProfile()
            private const val URL_PREFIX = "https://"
        }
    }

    fun updateProfile(
        name: String,
        profileUrl: String,
        description: String,
    ) = apply {
        profile = UserProfile(
            name = name,
            profileUrl = profileUrl,
            description = description,
        )
    }

    companion object {
        fun createDefault(): User = User()
    }
}
