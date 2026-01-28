package kr.wooco.woocobe.core.group.application.port.`in`.results

import kr.wooco.woocobe.core.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.user.domain.entity.User

data class GroupResult(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val inviteCode: String,
    val users: List<GroupUserResult>,
    val groupSize: Int,
) {
    data class GroupUserResult(
        val userId: Long,
        val name: String,
        val role: String,
        val profileUrl: String,
    )

    companion object {
        fun of(
            groupView: GroupView,
            users: List<User>,
        ): GroupResult {
            val userMap = users.associateBy { it.id }
            return GroupResult(
                id = groupView.id,
                ownerId = groupView.ownerId,
                name = groupView.name,
                inviteCode = groupView.inviteCode,
                users = groupView.users.map { groupUser ->
                    val user = requireNotNull(userMap[groupUser.userId])
                    GroupUserResult(
                        userId = groupUser.userId,
                        name = user.profile.name,
                        role = groupUser.role,
                        profileUrl = user.profile.profileUrl,
                    )
                },
                groupSize = groupView.groupSize,
            )
        }

        fun listOf(
            groupViews: List<GroupView>,
            users: List<User>,
        ): List<GroupResult> =
            groupViews.map { groupView ->
                of(groupView, users)
            }
    }
}
