package kr.wooco.woocobe.mysql.calendar.group

import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupCommandPort
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupQueryPort
import kr.wooco.woocobe.core.calendar.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.calendar.group.domain.entity.Group
import kr.wooco.woocobe.core.calendar.group.domain.entity.Group.Status
import kr.wooco.woocobe.core.calendar.group.domain.entity.GroupUser
import kr.wooco.woocobe.core.calendar.group.domain.exception.InvalidInviteCodeException
import kr.wooco.woocobe.core.calendar.group.domain.exception.NotExistsGroupException
import kr.wooco.woocobe.mysql.calendar.group.entity.GroupUserJpaEntity
import kr.wooco.woocobe.mysql.calendar.group.repository.GroupJpaRepository
import kr.wooco.woocobe.mysql.calendar.group.repository.GroupUserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class GroupPersistenceAdapter(
    private val groupJpaRepository: GroupJpaRepository,
    private val groupUserJpaRepository: GroupUserJpaRepository,
) : GroupCommandPort,
    GroupQueryPort {
    @Transactional
    override fun save(group: Group): Long {
        val groupJpaEntity = groupJpaRepository.save(GroupPersistenceMapper.toJpaEntity(group))
        groupUserJpaRepository.deleteAllInBatchByGroupId(groupJpaEntity.id)
        groupUserJpaRepository.saveAll(GroupUserJpaEntity.Companion.listOf(group, groupJpaEntity))
        return groupJpaEntity.id
    }

    override fun getById(groupId: Long): Group {
        val groupJpaEntity = groupJpaRepository.findByIdOrNull(id = groupId)
            ?: throw NotExistsGroupException
        val groupUserJpaEntities = findAllGroupUserActiveByGroupId(groupId)
        return GroupPersistenceMapper.toDomainEntity(groupJpaEntity, groupUserJpaEntities)
    }

    override fun getByInviteCode(inviteToken: String): Group {
        val groupJpaEntity = groupJpaRepository.findByInviteCode(inviteCode = inviteToken)
            ?: throw InvalidInviteCodeException
        val groupUserJpaEntities = findAllGroupUserActiveByGroupId(groupJpaEntity.id)
        return GroupPersistenceMapper.toDomainEntity(groupJpaEntity, groupUserJpaEntities)
    }

    override fun getViewByIdWithActive(groupId: Long): GroupView {
        val groupJpaEntity = groupJpaRepository.findByIdAndStatus(id = groupId, status = Status.ACTIVE.name)
            ?: throw NotExistsGroupException
        val groupUserJpaEntities = findAllGroupUserActiveByGroupId(groupId)
        return GroupPersistenceMapper.toReadModel(groupJpaEntity, groupUserJpaEntities)
    }

    override fun getViewAllByUserIdWithActive(userId: Long): List<GroupView> {
        val groupUserJpaEntities = groupUserJpaRepository.findAllByUserIdAndStatus(
            userId = userId,
            status = GroupUser.Status.ACTIVE.name,
        )
        val groupIds = groupUserJpaEntities.map { it.groupId }
        return getViewAllByIdsWithActive(groupIds)
    }

    override fun getViewAllByIdsWithActive(groupIds: List<Long>): List<GroupView> {
        val groupJpaEntities = groupJpaRepository.findAllByIdInAndStatus(
            groupIds = groupIds,
            status = GroupUser.Status.ACTIVE.name
        )
        val allGroupUsers = groupUserJpaRepository.findAllByGroupIdIn(groupIds)
        return GroupPersistenceMapper.toReadModels(
            groupJpaEntities = groupJpaEntities,
            groupUserJpaEntities = allGroupUsers,
        )
    }

    private fun findAllGroupUserActiveByGroupId(groupId: Long): List<GroupUserJpaEntity> =
        groupUserJpaRepository.findAllByGroupIdAndStatus(
            groupId = groupId,
            status = Status.ACTIVE.name,
        )
}
