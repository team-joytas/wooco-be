package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.ReadAllCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.ReadAllInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.ReadCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.results.CourseResult
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseQueryService(
    private val userQueryPort: UserQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val courseQueryPort: CourseQueryPort,
    private val loadInterestCoursePersistencePort: LoadInterestCoursePersistencePort,
) : ReadCourseUseCase,
    ReadAllCourseUseCase,
    ReadAllInterestCourseUseCase {
    @Transactional(readOnly = true)
    override fun readCourse(query: ReadCourseUseCase.Query): CourseResult {
        val course = courseQueryPort.getByCourseId(query.courseId)
        val user = userQueryPort.getByUserId(course.userId)
        val placeIds = course.coursePlaces.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        val isInterest = query.userId?.run {
            loadInterestCoursePersistencePort.existsByUserIdAndCourseId(query.userId, course.id)
        } ?: false
        return CourseResult.of(course, user, places, isInterest)
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
        val courses = courseQueryPort.getAllCourseByCondition(condition)
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
        val courses = courseQueryPort.getAllInterestCourseByCondition(condition)
        return fetchCourseDetails(query.userId, courses)
    }

    private fun fetchCourseDetails(
        userId: Long?,
        courses: List<Course>,
    ): List<CourseResult> {
        val userIds = courses.map { it.userId }.distinct()
        val users = userQueryPort.getAllByUserIds(userIds)
        val placeIds = courses.flatMap { it.coursePlaces }.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        val courseIds = courses.map { it.id }
        val interestCourseIds = userId?.run {
            loadInterestCoursePersistencePort.getAllCourseIdByUserIdAndCourseIds(userId, courseIds)
        } ?: emptyList()
        return CourseResult.listOf(courses, users, places, interestCourseIds)
    }
}
