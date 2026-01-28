package kr.wooco.woocobe.api.group.response

import kr.wooco.woocobe.core.group.application.port.`in`.results.GroupResult

data class GroupDetailResponse(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val inviteCode: String,
    val groupSize: Int,
    val users: List<GroupUserResponse>,
) {
    data class GroupUserResponse(
        val userId: Long,
        val name: String,
        val role: String,
        val profileUrl: String,
    )

    companion object {
        fun from(result: GroupResult): GroupDetailResponse =
            GroupDetailResponse(
                id = result.id,
                ownerId = result.ownerId,
                name = result.name,
                inviteCode = result.inviteCode,
                users = result.users.map { user ->
                    GroupUserResponse(
                        userId = user.userId,
                        name = user.name,
                        role = user.role,
                        profileUrl = user.profileUrl,
                    )
                },
                groupSize = result.groupSize,
            )

        fun listFrom(results: List<GroupResult>): List<GroupDetailResponse> = results.map { from(it) }
    }
}
