package kr.wooco.woocobe.core.group.application.port.out.dto

/**
 * 그룹 Read Model
 *
 * @author Junseoparkk
 */
data class GroupView(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val inviteCode: String,
    val users: List<GroupUserView>,
    val groupSize: Int,
) {
    data class GroupUserView(
        val userId: Long,
        val role: String,
    )
}
