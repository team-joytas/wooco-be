package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.ReadAllCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.ReadAllInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.ReadCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.results.CourseResult
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeQueryPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.application.port.out.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.dto.CourseView
import kr.wooco.woocobe.core.course.application.port.out.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseQueryService(
    private val userQueryPort: UserQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val courseQueryPort: CourseQueryPort,
    private val courseLikeQueryPort: CourseLikeQueryPort,
) : ReadCourseUseCase,
    ReadAllCourseUseCase,
    ReadAllInterestCourseUseCase {
    @Transactional(readOnly = true)
    override fun readCourse(query: ReadCourseUseCase.Query): CourseResult {
        val courseView = courseQueryPort.getViewByCourseId(query.courseId)
        val user = userQueryPort.getByUserId(courseView.userId)
        val placeIds = courseView.coursePlaces.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        val isInterest = query.userId?.run {
            courseLikeQueryPort.existsByUserIdAndCourseId(query.userId, courseView.id)
        } ?: false
        return CourseResult.of(courseView, user, places, isInterest)
    }

    @Transactional(readOnly = true)
    override fun readAllCourse(query: ReadAllCourseUseCase.Query): List<CourseResult> {
        val condition = CourseSearchCondition(
            writerId = query.writerId,
            category = query.category,
            primaryRegion = query.primaryRegion,
            secondaryRegion = query.secondaryRegion,
            limit = query.limit,
            sort = query.sort,
        )
        val courses = courseQueryPort.getViewAllCourseByCondition(condition)
        return fetchCourseDetails(query.userId, courses)
    }

    @Transactional(readOnly = true)
    override fun readAllUserInterestCourse(query: ReadAllInterestCourseUseCase.Query): List<CourseResult> {
        val condition = InterestCourseSearchCondition(
            targetUserId = query.targetUserId,
            category = query.category,
            primaryRegion = query.primaryRegion,
            secondaryRegion = query.secondaryRegion,
            limit = query.limit,
            sort = query.sort,
        )
        val courseViews = courseQueryPort.getAllViewInterestCourseByCondition(condition)
        return fetchCourseDetails(query.userId, courseViews)
    }

    private fun fetchCourseDetails(
        userId: Long?,
        courseViews: List<CourseView>,
    ): List<CourseResult> {
        val userIds = courseViews.map { it.userId }.distinct()
        val users = userQueryPort.getAllByUserIds(userIds)
        val placeIds = courseViews.flatMap { it.coursePlaces }.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        val courseIds = courseViews.map { it.id }
        val interestCourseIds = userId?.run {
            courseLikeQueryPort.getAllCourseIdByUserIdAndCourseIds(userId, courseIds)
        } ?: emptyList()
        return CourseResult.listOf(courseViews, users, places, interestCourseIds)
    }
}
