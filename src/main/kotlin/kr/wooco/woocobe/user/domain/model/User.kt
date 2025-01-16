package kr.wooco.woocobe.user.domain.model

class User(
    val id: Long,
    var name: String,
    var profileUrl: String,
    var description: String,
) {
    fun update(
        name: String,
        profileUrl: String,
        description: String,
    ) = apply {
        this.name = name
        this.profileUrl = profileUrl
        this.description = description
    }

    companion object {
        fun register(
            userId: Long = 0L,
            name: String = "",
            profileUrl: String = "",
            description: String = "",
        ): User =
            User(
                id = userId,
                name = name,
                profileUrl = profileUrl,
                description = description,
            )
    }
}
