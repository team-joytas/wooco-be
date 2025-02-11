package kr.wooco.woocobe.course.application.service

import kr.wooco.woocobe.course.application.port.`in`.ReadAllCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.ReadAllInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.ReadCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.results.CourseResult
import kr.wooco.woocobe.course.application.port.out.LoadCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.course.application.service.dto.CourseSearchCondition
import kr.wooco.woocobe.course.application.service.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.course.domain.entity.Course
import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseQueryService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
    private val loadCoursePersistencePort: LoadCoursePersistencePort,
    private val loadInterestCoursePersistencePort: LoadInterestCoursePersistencePort,
) : ReadCourseUseCase,
    ReadAllCourseUseCase,
    ReadAllInterestCourseUseCase {
    @Transactional(readOnly = true)
    override fun readCourse(query: ReadCourseUseCase.Query): CourseResult {
        val course = loadCoursePersistencePort.getByCourseId(query.courseId)
        val user = loadUserPersistencePort.getByUserId(course.userId)
        val placeIds = course.coursePlaces.map { it.placeId }.distinct()
        val places = loadPlacePersistencePort.getAllByPlaceIds(placeIds)
        val isInterest = query.userId?.run {
            loadInterestCoursePersistencePort.existsByUserIdAndCourseId(course.id, query.userId)
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
        val courses = loadCoursePersistencePort.getAllCourseByCondition(condition)
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
        val courses = loadCoursePersistencePort.getAllInterestCourseByCondition(condition)
        return fetchCourseDetails(query.userId, courses)
    }

    private fun fetchCourseDetails(
        userId: Long?,
        courses: List<Course>,
    ): List<CourseResult> {
        val userIds = courses.map { it.userId }.distinct()
        val users = loadUserPersistencePort.getAllByUserIds(userIds)
        val placeIds = courses.flatMap { it.coursePlaces }.map { it.placeId }.distinct()
        val places = loadPlacePersistencePort.getAllByPlaceIds(placeIds)
        val courseIds = courses.map { it.id }
        val interestCourseIds = userId?.run {
            loadInterestCoursePersistencePort.getAllCourseIdByUserIdAndCourseIds(userId, courseIds)
        } ?: emptyList()
        return CourseResult.listOf(courses, users, places, interestCourseIds)
    }
}
