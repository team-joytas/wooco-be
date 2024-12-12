package kr.wooco.woocobe.user.domain.model

// TODO: 랜덤 닉네임 생성 로직이 필요
class User(
    val id: Long,
    var name: String = "",
) {
    companion object {
        fun register(userId: Long): User =
            User(
                id = userId,
            )
    }
}
