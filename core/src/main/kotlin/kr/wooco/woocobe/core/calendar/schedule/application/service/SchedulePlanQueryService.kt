package kr.wooco.woocobe.core.calendar.schedule.application.service

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupQueryPort
import kr.wooco.woocobe.core.calendar.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.ReadAllPlanByDateUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.ReadPlanUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.results.PlanResult
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanQueryPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchedulePlanQueryService(
    private val userQueryPort: UserQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val schedulePlanQueryPort: SchedulePlanQueryPort,
    private val groupQueryPort: GroupQueryPort,
) : ReadPlanUseCase,
    ReadAllPlanByDateUseCase {
    @Transactional(readOnly = true)
    override fun readPlan(query: ReadPlanUseCase.Query): PlanResult {
        val planView = schedulePlanQueryPort.getViewById(query.planId)
        val groupView = groupQueryPort.getViewById(planView.groupId)
        val users = userQueryPort.getAllByUserIds(groupView.users.map { it.userId })
        val places = placeQueryPort.getAllByPlaceIds(planView.places.map { it.placeId })
            .associateBy { it.id }
            .values
            .toList()

        val groupResult = GroupResult.of(
            groupView = groupView,
            users = users,
        )

        return PlanResult.of(
            group = groupResult,
            planView = planView,
            places = places,
        )
    }

    override fun readAllPlanByDate(query: ReadAllPlanByDateUseCase.Query): List<PlanResult> {
        val groups = groupQueryPort.getViewAllByUserId(query.userId)

        val planViews = schedulePlanQueryPort.getViewAllByGroupIdInAndVisitDate(
            groupIds = groups.map { it.id },
            visitDate = query.date
        )

        val groupResultMap = createGroupResults(groups)
        val placeMap = createPlaceMap(planViews)

        return PlanResult.Companion.listOf(
            groups = groupResultMap,
            planViews = planViews,
            placeMap = placeMap,
        )
    }

    private fun createGroupResults(groups: List<GroupView>): Map<Long, GroupResult> {
        val allGroupUsers = groups.flatMap { group -> group.users }
        val userIds = allGroupUsers.map { it.userId }.distinct()
        val users = userQueryPort.getAllByUserIds(userIds)
        val userMap = users.associateBy { it.id }

        return groups.associate { groupView ->
            val groupUsers = groupView.users.map { groupUser ->
                requireNotNull(userMap[groupUser.userId])
            }
            groupView.id to GroupResult.of(
                groupView = groupView,
                users = groupUsers,
            )
        }
    }

    private fun createPlaceMap(planViews: List<PlanView>): Map<Long, Place> {
        val placeIds = planViews
            .flatMap { it.places.map { planPlace -> planPlace.placeId } }
            .distinct()
        return placeQueryPort.getAllByPlaceIds(placeIds)
            .associateBy { it.id }
    }
}
