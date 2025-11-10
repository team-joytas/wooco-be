package kr.wooco.woocobe.mysql.group

import kr.wooco.woocobe.core.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.group.domain.entity.Group
import kr.wooco.woocobe.core.group.domain.vo.GroupInviteCode
import kr.wooco.woocobe.mysql.group.entity.GroupJpaEntity
import kr.wooco.woocobe.mysql.group.entity.GroupUserJpaEntity

internal object GroupPersistenceMapper {
    fun toDomainEntity(
        groupJpaEntity: GroupJpaEntity,
        groupUserJpaEntities: List<GroupUserJpaEntity>
    ): Group =
        Group(
            id = groupJpaEntity.id,
            ownerId = groupJpaEntity.ownerId,
            name = Group.Name(groupJpaEntity.name),
            type = Group.Type.valueOf(groupJpaEntity.type),
            inviteCode = GroupInviteCode(groupJpaEntity.inviteCode),
            users = groupUserJpaEntities.map(GroupUserPersistenceMapper::toDomainEntity),
            status = Group.Status.valueOf(groupJpaEntity.status),
        )

    fun toJpaEntity(group: Group): GroupJpaEntity =
        GroupJpaEntity(
            id = group.id,
            ownerId = group.ownerId,
            name = group.name.value,
            type = group.type.name,
            inviteCode = group.inviteCode.value,
            status = group.status.name,
        )

    fun toReadModel(
        groupJpaEntity: GroupJpaEntity,
        groupUserJpaEntities: List<GroupUserJpaEntity>,
    ): GroupView =
        GroupView(
            id = groupJpaEntity.id,
            ownerId = groupJpaEntity.ownerId,
            name = groupJpaEntity.name,
            type = groupJpaEntity.type,
            inviteCode = groupJpaEntity.inviteCode,
            users = groupUserJpaEntities.map {
                GroupView.GroupUserView(
                    userId = it.userId,
                    role = it.role,
                )
            },
            groupSize = groupUserJpaEntities.size,
        )
}
