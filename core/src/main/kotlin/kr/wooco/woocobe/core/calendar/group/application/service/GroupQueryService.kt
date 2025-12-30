package kr.wooco.woocobe.core.calendar.group.application.service

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.ReadAllGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.ReadGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupQueryPort
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
        val group = groupQueryPort.getViewByIdWithActive(query.groupId)
        val userIds = group.users.map { it.userId }
        val users = userQueryPort.getAllByUserIds(userIds)
        return GroupResult.Companion.of(groupView = group, users = users)
    }

    @Transactional(readOnly = true)
    override fun readAllGroup(query: ReadAllGroupUseCase.Query): List<GroupResult> {
        val groups = groupQueryPort.getViewAllByUserIdWithActive(query.userId)
        val userIds = groups
            .flatMap { it.users.map { u -> u.userId } }
            .distinct()
        val users = userQueryPort.getAllByUserIds(userIds)
        return GroupResult.Companion.listOf(groupViews = groups, users = users)
    }
}
