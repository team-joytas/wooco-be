package kr.wooco.woocobe.core.group.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.DomainEntity
import java.time.LocalDate

data class GroupUser(
    override val id: Long,
    val groupId: Long,
    val userId: Long,
    val role: Role,
    val joinedAt: JoinedAt,
    val status: Status,
) : DomainEntity() {

    @JvmInline
    value class JoinedAt(
        val value: LocalDate,
    )

    enum class Role { OWNER, MEMBER }

    enum class Status { ACTIVE, DELETED }

    companion object {
        fun createOwner(groupId: Long, userId: Long): GroupUser =
            create(groupId, userId, Role.OWNER)

        fun createMember(groupId: Long, userId: Long): GroupUser =
            create(groupId, userId, Role.MEMBER)

        private fun create(
            groupId: Long,
            userId: Long,
            role: Role,
        ): GroupUser =
            GroupUser(
                id = 0L,
                groupId = groupId,
                userId = userId,
                role = role,
                joinedAt = JoinedAt(LocalDate.now()),
                status = Status.ACTIVE,
            )
    }
}
