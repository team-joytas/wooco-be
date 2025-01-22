package kr.wooco.woocobe.course.ui.web.facade

import kr.wooco.woocobe.course.domain.usecase.CheckInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.CheckInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllInterestCourseIdInput
import kr.wooco.woocobe.course.domain.usecase.GetAllInterestCourseIdUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllUserCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllUserCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllUserInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllUserInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetCourseUseCase
import kr.wooco.woocobe.course.ui.web.controller.response.CourseDetailResponse
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceUseCase
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import org.springframework.stereotype.Service

// TODO: 리팩토링

@Service
class CourseQueryFacade(
    private val getCourseUseCase: GetCourseUseCase,
    private val getAllCourseUseCase: GetAllCourseUseCase,
    private val getAllUserCourseUseCase: GetAllUserCourseUseCase,
    private val checkInterestCourseUseCase: CheckInterestCourseUseCase,
    private val getAllInterestCourseIdUseCase: GetAllInterestCourseIdUseCase,
    private val getAllUserInterestCourseUseCase: GetAllUserInterestCourseUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
    private val getAllPlaceUseCase: GetAllPlaceUseCase,
) {
    fun getCourseDetail(
        userId: Long?,
        courseId: Long,
    ): CourseDetailResponse {
        val getCourseResult = getCourseUseCase.execute(GetCourseInput(courseId = courseId))

        val placeIds = getCourseResult.course.coursePlaces.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val getUserResult = getUserUseCase.execute(GetUserInput(userId = getCourseResult.course.userId))

        val checkInterestCourseResult = userId?.run {
            checkInterestCourseUseCase.execute(CheckInterestCourseInput(userId = userId, courseId = courseId))
        }

        return CourseDetailResponse.of(
            course = getCourseResult.course,
            places = getAllPlaceResult.places,
            user = getUserResult.user,
            isInterest = checkInterestCourseResult?.isInterested ?: false,
        )
    }

    fun getAllCourseDetail(
        userId: Long?,
        primaryRegion: String?,
        secondaryRegion: String?,
        category: String?,
        limit: Int?,
        sort: String,
    ): List<CourseDetailResponse> {
        val getAllCourseResult = getAllCourseUseCase.execute(
            GetAllCourseInput(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
                category = category,
                limit = limit,
                sort = sort,
            ),
        )

        val placeIds = getAllCourseResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val writerIds = getAllCourseResult.courses.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = writerIds))

        val courseIds = getAllCourseResult.courses.map { it.id }
        val getAllInterestCourseIdResult = userId?.run {
            getAllInterestCourseIdUseCase.execute(GetAllInterestCourseIdInput(userId = userId, courseIds = courseIds))
        }

        return CourseDetailResponse.listOf(
            courses = getAllCourseResult.courses,
            places = getAllPlaceResult.places,
            users = getAllUserResult.users,
            interestCourseIds = getAllInterestCourseIdResult?.interestCourseIds ?: emptyList(),
        )
    }

    fun getAllUserCourseDetail(
        currentUserId: Long?,
        userId: Long,
        sort: String,
    ): List<CourseDetailResponse> {
        val getAllUserCourseResult =
            getAllUserCourseUseCase.execute(GetAllUserCourseInput(userId = userId, sort = sort))

        val placeIds = getAllUserCourseResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = listOf(userId)))

        val getAllInterestCourseIdResult = currentUserId?.run {
            getAllInterestCourseIdUseCase.execute(
                GetAllInterestCourseIdInput(
                    userId = currentUserId,
                    courseIds = getAllUserCourseResult.courses.map { it.id },
                ),
            )
        }

        return CourseDetailResponse.listOf(
            courses = getAllUserCourseResult.courses,
            places = getAllPlaceResult.places,
            users = getAllUserResult.users,
            interestCourseIds = getAllInterestCourseIdResult?.interestCourseIds ?: emptyList(),
        )
    }

    fun getAllUserInterestCourse(
        currentUserId: Long?,
        userId: Long,
        limit: Int?,
    ): List<CourseDetailResponse> {
        val getAllUserInterestCourseResult =
            getAllUserInterestCourseUseCase.execute(GetAllUserInterestCourseInput(userId = userId, limit = limit))

        val writerIds = getAllUserInterestCourseResult.courses.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = writerIds))

        val placeIds = getAllUserInterestCourseResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val getAllInterestCourseIdResult = currentUserId?.run {
            getAllInterestCourseIdUseCase.execute(
                GetAllInterestCourseIdInput(
                    userId = currentUserId,
                    courseIds = getAllUserInterestCourseResult.courses.map { it.id },
                ),
            )
        }

        return CourseDetailResponse.listOf(
            courses = getAllUserInterestCourseResult.courses,
            places = getAllPlaceResult.places,
            users = getAllUserResult.users,
            interestCourseIds = getAllInterestCourseIdResult?.interestCourseIds ?: emptyList(),
        )
    }
}
