package kr.wooco.woocobe.user.domain.model

class User(
    val id: Long,
    var name: String,
    var profileUrl: String,
) {
    fun update(
        name: String,
        profileUrl: String,
    ) = apply {
        this.name = name
        this.profileUrl = profileUrl
    }

    companion object {
        fun register(
            userId: Long = 0L,
            name: String = "",
            profileUrl: String = "",
        ): User =
            User(
                id = userId,
                name = name,
                profileUrl = profileUrl,
            )
    }
}
