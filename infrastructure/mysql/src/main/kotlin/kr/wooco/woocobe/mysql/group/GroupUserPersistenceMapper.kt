package kr.wooco.woocobe.mysql.group

import kr.wooco.woocobe.core.group.domain.entity.GroupUser
import kr.wooco.woocobe.mysql.group.entity.GroupUserJpaEntity

internal object GroupUserPersistenceMapper {
    fun toDomainEntity(groupUserJpaEntity: GroupUserJpaEntity): GroupUser =
        GroupUser(
            id = groupUserJpaEntity.id,
            groupId = groupUserJpaEntity.groupId,
            userId = groupUserJpaEntity.userId,
            role = GroupUser.Role.valueOf(groupUserJpaEntity.role),
            joinedAt = GroupUser.JoinedAt(groupUserJpaEntity.joinedAt),
            status = GroupUser.Status.valueOf(groupUserJpaEntity.status),
        )
}
