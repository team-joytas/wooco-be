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
