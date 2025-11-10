package kr.wooco.woocobe.core.group.application.service

import kr.wooco.woocobe.core.group.application.port.`in`.ReadAllGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.ReadGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.group.application.port.out.GroupQueryPort
import kr.wooco.woocobe.core.group.domain.entity.Group.Status
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupQueryService(
    private val groupQueryPort: GroupQueryPort,
    private val userQueryPort: UserQueryPort,
) : ReadGroupUseCase,
    ReadAllGroupUseCase {
    @Transactional(readOnly = true)
    override fun readGroup(query: ReadGroupUseCase.Query): GroupResult {
        val group = groupQueryPort.getViewByIdAndStatus(groupId = query.groupId, status = Status.ACTIVE)
        val users = userQueryPort.getAllByUserIds(group.users.map { it.userId })
        return GroupResult.of(groupView = group, users = users)
    }

    @Transactional(readOnly = true)
    override fun readAllGroup(query: ReadAllGroupUseCase.Query): List<GroupResult> {
        val groups = groupQueryPort.getViewAllByUserIdAndStatus(query.userId, status = Status.ACTIVE)
        val userIds = groups.flatMap { group -> group.users.map { it.userId } }.distinct()
        val userMap = userQueryPort.getAllByUserIds(userIds).associateBy { it.id }

        return groups.map { group ->
            val groupUsers = group.users.mapNotNull { user -> userMap[user.userId] }
            GroupResult.of(groupView = group, users = groupUsers)
        }
    }
}
